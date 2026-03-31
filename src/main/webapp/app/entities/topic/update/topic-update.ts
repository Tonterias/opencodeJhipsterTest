import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPost } from 'app/entities/post/post.model';
import { PostService } from 'app/entities/post/service/post.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { TopicService } from '../service/topic.service';
import { ITopic } from '../topic.model';

import { TopicFormGroup, TopicFormService } from './topic-form.service';

@Component({
  selector: 'jhi-topic-update',
  templateUrl: './topic-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class TopicUpdate implements OnInit {
  readonly isSaving = signal(false);
  topic: ITopic | null = null;

  postsSharedCollection = signal<IPost[]>([]);

  protected topicService = inject(TopicService);
  protected topicFormService = inject(TopicFormService);
  protected postService = inject(PostService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TopicFormGroup = this.topicFormService.createTopicFormGroup();

  comparePost = (o1: IPost | null, o2: IPost | null): boolean => this.postService.comparePost(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ topic }) => {
      this.topic = topic;
      if (topic) {
        this.updateForm(topic);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const topic = this.topicFormService.getTopic(this.editForm);
    if (topic.id === null) {
      this.subscribeToSaveResponse(this.topicService.create(topic));
    } else {
      this.subscribeToSaveResponse(this.topicService.update(topic));
    }
  }

  protected subscribeToSaveResponse(result: Observable<ITopic | null>): void {
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

  protected updateForm(topic: ITopic): void {
    this.topic = topic;
    this.topicFormService.resetForm(this.editForm, topic);

    this.postsSharedCollection.update(posts => this.postService.addPostToCollectionIfMissing<IPost>(posts, ...(topic.posts ?? [])));
  }

  protected loadRelationshipsOptions(): void {
    this.postService
      .query()
      .pipe(map((res: HttpResponse<IPost[]>) => res.body ?? []))
      .pipe(map((posts: IPost[]) => this.postService.addPostToCollectionIfMissing<IPost>(posts, ...(this.topic?.posts ?? []))))
      .subscribe((posts: IPost[]) => this.postsSharedCollection.set(posts));
  }
}
