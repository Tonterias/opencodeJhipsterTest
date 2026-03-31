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
import { ICactivity } from 'app/entities/cactivity/cactivity.model';
import { CactivityService } from 'app/entities/cactivity/service/cactivity.service';
import { ICceleb } from 'app/entities/cceleb/cceleb.model';
import { CcelebService } from 'app/entities/cceleb/service/cceleb.service';
import { ICinterest } from 'app/entities/cinterest/cinterest.model';
import { CinterestService } from 'app/entities/cinterest/service/cinterest.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { AlertErrorModel } from 'app/shared/alert/alert-error.model';
import { TranslateDirective } from 'app/shared/language';
import { ICommunity } from '../community.model';
import { CommunityService } from '../service/community.service';

import { CommunityFormGroup, CommunityFormService } from './community-form.service';

@Component({
  selector: 'jhi-community-update',
  templateUrl: './community-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class CommunityUpdate implements OnInit {
  readonly isSaving = signal(false);
  community: ICommunity | null = null;

  appusersSharedCollection = signal<IAppuser[]>([]);
  cinterestsSharedCollection = signal<ICinterest[]>([]);
  cactivitiesSharedCollection = signal<ICactivity[]>([]);
  ccelebsSharedCollection = signal<ICceleb[]>([]);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected communityService = inject(CommunityService);
  protected communityFormService = inject(CommunityFormService);
  protected appuserService = inject(AppuserService);
  protected cinterestService = inject(CinterestService);
  protected cactivityService = inject(CactivityService);
  protected ccelebService = inject(CcelebService);
  protected elementRef = inject(ElementRef);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CommunityFormGroup = this.communityFormService.createCommunityFormGroup();

  compareAppuser = (o1: IAppuser | null, o2: IAppuser | null): boolean => this.appuserService.compareAppuser(o1, o2);

  compareCinterest = (o1: ICinterest | null, o2: ICinterest | null): boolean => this.cinterestService.compareCinterest(o1, o2);

  compareCactivity = (o1: ICactivity | null, o2: ICactivity | null): boolean => this.cactivityService.compareCactivity(o1, o2);

  compareCceleb = (o1: ICceleb | null, o2: ICceleb | null): boolean => this.ccelebService.compareCceleb(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ community }) => {
      this.community = community;
      if (community) {
        this.updateForm(community);
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
    const community = this.communityFormService.getCommunity(this.editForm);
    if (community.id === null) {
      this.subscribeToSaveResponse(this.communityService.create(community));
    } else {
      this.subscribeToSaveResponse(this.communityService.update(community));
    }
  }

  protected subscribeToSaveResponse(result: Observable<ICommunity | null>): void {
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

  protected updateForm(community: ICommunity): void {
    this.community = community;
    this.communityFormService.resetForm(this.editForm, community);

    this.appusersSharedCollection.update(appusers =>
      this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, community.appuser),
    );
    this.cinterestsSharedCollection.update(cinterests =>
      this.cinterestService.addCinterestToCollectionIfMissing<ICinterest>(cinterests, ...(community.cinterests ?? [])),
    );
    this.cactivitiesSharedCollection.update(cactivities =>
      this.cactivityService.addCactivityToCollectionIfMissing<ICactivity>(cactivities, ...(community.cactivities ?? [])),
    );
    this.ccelebsSharedCollection.update(ccelebs =>
      this.ccelebService.addCcelebToCollectionIfMissing<ICceleb>(ccelebs, ...(community.ccelebs ?? [])),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.appuserService
      .query()
      .pipe(map((res: HttpResponse<IAppuser[]>) => res.body ?? []))
      .pipe(map((appusers: IAppuser[]) => this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, this.community?.appuser)))
      .subscribe((appusers: IAppuser[]) => this.appusersSharedCollection.set(appusers));

    this.cinterestService
      .query()
      .pipe(map((res: HttpResponse<ICinterest[]>) => res.body ?? []))
      .pipe(
        map((cinterests: ICinterest[]) =>
          this.cinterestService.addCinterestToCollectionIfMissing<ICinterest>(cinterests, ...(this.community?.cinterests ?? [])),
        ),
      )
      .subscribe((cinterests: ICinterest[]) => this.cinterestsSharedCollection.set(cinterests));

    this.cactivityService
      .query()
      .pipe(map((res: HttpResponse<ICactivity[]>) => res.body ?? []))
      .pipe(
        map((cactivities: ICactivity[]) =>
          this.cactivityService.addCactivityToCollectionIfMissing<ICactivity>(cactivities, ...(this.community?.cactivities ?? [])),
        ),
      )
      .subscribe((cactivities: ICactivity[]) => this.cactivitiesSharedCollection.set(cactivities));

    this.ccelebService
      .query()
      .pipe(map((res: HttpResponse<ICceleb[]>) => res.body ?? []))
      .pipe(
        map((ccelebs: ICceleb[]) =>
          this.ccelebService.addCcelebToCollectionIfMissing<ICceleb>(ccelebs, ...(this.community?.ccelebs ?? [])),
        ),
      )
      .subscribe((ccelebs: ICceleb[]) => this.ccelebsSharedCollection.set(ccelebs));
  }
}
