import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { AppuserService } from 'app/entities/appuser/service/appuser.service';
import { IPost } from 'app/entities/post/post.model';
import { PostService } from 'app/entities/post/service/post.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IComment } from '../comment.model';
import { CommentService } from '../service/comment.service';

import { CommentFormGroup, CommentFormService } from './comment-form.service';

@Component({
  selector: 'jhi-comment-update',
  templateUrl: './comment-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class CommentUpdate implements OnInit {
  readonly isSaving = signal(false);
  comment: IComment | null = null;

  appusersSharedCollection = signal<IAppuser[]>([]);
  postsSharedCollection = signal<IPost[]>([]);

  protected commentService = inject(CommentService);
  protected commentFormService = inject(CommentFormService);
  protected appuserService = inject(AppuserService);
  protected postService = inject(PostService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CommentFormGroup = this.commentFormService.createCommentFormGroup();

  compareAppuser = (o1: IAppuser | null, o2: IAppuser | null): boolean => this.appuserService.compareAppuser(o1, o2);

  comparePost = (o1: IPost | null, o2: IPost | null): boolean => this.postService.comparePost(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comment }) => {
      this.comment = comment;
      if (comment) {
        this.updateForm(comment);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const comment = this.commentFormService.getComment(this.editForm);
    if (comment.id === null) {
      this.subscribeToSaveResponse(this.commentService.create(comment));
    } else {
      this.subscribeToSaveResponse(this.commentService.update(comment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IComment | null>): void {
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

  protected updateForm(comment: IComment): void {
    this.comment = comment;
    this.commentFormService.resetForm(this.editForm, comment);

    this.appusersSharedCollection.update(appusers =>
      this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, comment.appuser),
    );
    this.postsSharedCollection.update(posts => this.postService.addPostToCollectionIfMissing<IPost>(posts, comment.post));
  }

  protected loadRelationshipsOptions(): void {
    this.appuserService
      .query()
      .pipe(map((res: HttpResponse<IAppuser[]>) => res.body ?? []))
      .pipe(map((appusers: IAppuser[]) => this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, this.comment?.appuser)))
      .subscribe((appusers: IAppuser[]) => this.appusersSharedCollection.set(appusers));

    this.postService
      .query()
      .pipe(map((res: HttpResponse<IPost[]>) => res.body ?? []))
      .pipe(map((posts: IPost[]) => this.postService.addPostToCollectionIfMissing<IPost>(posts, this.comment?.post)))
      .subscribe((posts: IPost[]) => this.postsSharedCollection.set(posts));
  }
}
