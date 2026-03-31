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
import { IBlog } from 'app/entities/blog/blog.model';
import { BlogService } from 'app/entities/blog/service/blog.service';
import { TagService } from 'app/entities/tag/service/tag.service';
import { ITag } from 'app/entities/tag/tag.model';
import { TopicService } from 'app/entities/topic/service/topic.service';
import { ITopic } from 'app/entities/topic/topic.model';
import { AlertError } from 'app/shared/alert/alert-error';
import { AlertErrorModel } from 'app/shared/alert/alert-error.model';
import { TranslateDirective } from 'app/shared/language';
import { IPost } from '../post.model';
import { PostService } from '../service/post.service';

import { PostFormGroup, PostFormService } from './post-form.service';

@Component({
  selector: 'jhi-post-update',
  templateUrl: './post-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class PostUpdate implements OnInit {
  readonly isSaving = signal(false);
  post: IPost | null = null;

  appusersSharedCollection = signal<IAppuser[]>([]);
  blogsSharedCollection = signal<IBlog[]>([]);
  tagsSharedCollection = signal<ITag[]>([]);
  topicsSharedCollection = signal<ITopic[]>([]);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected postService = inject(PostService);
  protected postFormService = inject(PostFormService);
  protected appuserService = inject(AppuserService);
  protected blogService = inject(BlogService);
  protected tagService = inject(TagService);
  protected topicService = inject(TopicService);
  protected elementRef = inject(ElementRef);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PostFormGroup = this.postFormService.createPostFormGroup();

  compareAppuser = (o1: IAppuser | null, o2: IAppuser | null): boolean => this.appuserService.compareAppuser(o1, o2);

  compareBlog = (o1: IBlog | null, o2: IBlog | null): boolean => this.blogService.compareBlog(o1, o2);

  compareTag = (o1: ITag | null, o2: ITag | null): boolean => this.tagService.compareTag(o1, o2);

  compareTopic = (o1: ITopic | null, o2: ITopic | null): boolean => this.topicService.compareTopic(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ post }) => {
      this.post = post;
      if (post) {
        this.updateForm(post);
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
    const post = this.postFormService.getPost(this.editForm);
    if (post.id === null) {
      this.subscribeToSaveResponse(this.postService.create(post));
    } else {
      this.subscribeToSaveResponse(this.postService.update(post));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IPost | null>): void {
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

  protected updateForm(post: IPost): void {
    this.post = post;
    this.postFormService.resetForm(this.editForm, post);

    this.appusersSharedCollection.update(appusers => this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, post.appuser));
    this.blogsSharedCollection.update(blogs => this.blogService.addBlogToCollectionIfMissing<IBlog>(blogs, post.blog));
    this.tagsSharedCollection.update(tags => this.tagService.addTagToCollectionIfMissing<ITag>(tags, ...(post.tags ?? [])));
    this.topicsSharedCollection.update(topics => this.topicService.addTopicToCollectionIfMissing<ITopic>(topics, ...(post.topics ?? [])));
  }

  protected loadRelationshipsOptions(): void {
    this.appuserService
      .query()
      .pipe(map((res: HttpResponse<IAppuser[]>) => res.body ?? []))
      .pipe(map((appusers: IAppuser[]) => this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, this.post?.appuser)))
      .subscribe((appusers: IAppuser[]) => this.appusersSharedCollection.set(appusers));

    this.blogService
      .query()
      .pipe(map((res: HttpResponse<IBlog[]>) => res.body ?? []))
      .pipe(map((blogs: IBlog[]) => this.blogService.addBlogToCollectionIfMissing<IBlog>(blogs, this.post?.blog)))
      .subscribe((blogs: IBlog[]) => this.blogsSharedCollection.set(blogs));

    this.tagService
      .query()
      .pipe(map((res: HttpResponse<ITag[]>) => res.body ?? []))
      .pipe(map((tags: ITag[]) => this.tagService.addTagToCollectionIfMissing<ITag>(tags, ...(this.post?.tags ?? []))))
      .subscribe((tags: ITag[]) => this.tagsSharedCollection.set(tags));

    this.topicService
      .query()
      .pipe(map((res: HttpResponse<ITopic[]>) => res.body ?? []))
      .pipe(map((topics: ITopic[]) => this.topicService.addTopicToCollectionIfMissing<ITopic>(topics, ...(this.post?.topics ?? []))))
      .subscribe((topics: ITopic[]) => this.topicsSharedCollection.set(topics));
  }
}
