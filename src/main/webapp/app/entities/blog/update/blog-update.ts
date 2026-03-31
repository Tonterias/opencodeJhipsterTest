import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { IAppuser } from 'app/entities/appuser/appuser.model';
import { AppuserService } from 'app/entities/appuser/service/appuser.service';
import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { AlertErrorModel } from 'app/shared/alert/alert-error.model';
import { TranslateDirective } from 'app/shared/language';
import { IBlog } from '../blog.model';
import { BlogService } from '../service/blog.service';

import { BlogFormGroup, BlogFormService } from './blog-form.service';

@Component({
  selector: 'jhi-blog-update',
  templateUrl: './blog-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class BlogUpdate implements OnInit {
  readonly isSaving = signal(false);
  blog: IBlog | null = null;

  appusersSharedCollection = signal<IAppuser[]>([]);
  communitiesSharedCollection = signal<ICommunity[]>([]);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected blogService = inject(BlogService);
  protected blogFormService = inject(BlogFormService);
  protected appuserService = inject(AppuserService);
  protected communityService = inject(CommunityService);
  protected elementRef = inject(ElementRef);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BlogFormGroup = this.blogFormService.createBlogFormGroup();

  compareAppuser = (o1: IAppuser | null, o2: IAppuser | null): boolean => this.appuserService.compareAppuser(o1, o2);

  compareCommunity = (o1: ICommunity | null, o2: ICommunity | null): boolean => this.communityService.compareCommunity(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ blog }) => {
      this.blog = blog;
      if (blog) {
        this.updateForm(blog);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertErrorModel>('opencodetestApp.error', { ...err, key: `error.file.${err.key}` }),
        ),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector(`#${idInput}`)) {
      this.elementRef.nativeElement.querySelector(`#${idInput}`).value = null;
    }
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const blog = this.blogFormService.getBlog(this.editForm);
    if (blog.id === null) {
      this.subscribeToSaveResponse(this.blogService.create(blog));
    } else {
      this.subscribeToSaveResponse(this.blogService.update(blog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IBlog | null>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving.set(false);
  }

  protected updateForm(blog: IBlog): void {
    this.blog = blog;
    this.blogFormService.resetForm(this.editForm, blog);

    this.appusersSharedCollection.update(appusers => this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, blog.appuser));
    this.communitiesSharedCollection.update(communities =>
      this.communityService.addCommunityToCollectionIfMissing<ICommunity>(communities, blog.community),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.appuserService
      .query()
      .pipe(map((res: HttpResponse<IAppuser[]>) => res.body ?? []))
      .pipe(map((appusers: IAppuser[]) => this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, this.blog?.appuser)))
      .subscribe((appusers: IAppuser[]) => this.appusersSharedCollection.set(appusers));

    this.communityService
      .query()
      .pipe(map((res: HttpResponse<ICommunity[]>) => res.body ?? []))
      .pipe(
        map((communities: ICommunity[]) =>
          this.communityService.addCommunityToCollectionIfMissing<ICommunity>(communities, this.blog?.community),
        ),
      )
      .subscribe((communities: ICommunity[]) => this.communitiesSharedCollection.set(communities));
  }
}
