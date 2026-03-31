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
import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IFollow } from '../follow.model';
import { FollowService } from '../service/follow.service';

import { FollowFormGroup, FollowFormService } from './follow-form.service';

@Component({
  selector: 'jhi-follow-update',
  templateUrl: './follow-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class FollowUpdate implements OnInit {
  readonly isSaving = signal(false);
  follow: IFollow | null = null;

  appusersSharedCollection = signal<IAppuser[]>([]);
  communitiesSharedCollection = signal<ICommunity[]>([]);

  protected followService = inject(FollowService);
  protected followFormService = inject(FollowFormService);
  protected appuserService = inject(AppuserService);
  protected communityService = inject(CommunityService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FollowFormGroup = this.followFormService.createFollowFormGroup();

  compareAppuser = (o1: IAppuser | null, o2: IAppuser | null): boolean => this.appuserService.compareAppuser(o1, o2);

  compareCommunity = (o1: ICommunity | null, o2: ICommunity | null): boolean => this.communityService.compareCommunity(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ follow }) => {
      this.follow = follow;
      if (follow) {
        this.updateForm(follow);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const follow = this.followFormService.getFollow(this.editForm);
    if (follow.id === null) {
      this.subscribeToSaveResponse(this.followService.create(follow));
    } else {
      this.subscribeToSaveResponse(this.followService.update(follow));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IFollow | null>): void {
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

  protected updateForm(follow: IFollow): void {
    this.follow = follow;
    this.followFormService.resetForm(this.editForm, follow);

    this.appusersSharedCollection.update(appusers =>
      this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, follow.followed, follow.following),
    );
    this.communitiesSharedCollection.update(communities =>
      this.communityService.addCommunityToCollectionIfMissing<ICommunity>(communities, follow.cfollowed, follow.cfollowing),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.appuserService
      .query()
      .pipe(map((res: HttpResponse<IAppuser[]>) => res.body ?? []))
      .pipe(
        map((appusers: IAppuser[]) =>
          this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, this.follow?.followed, this.follow?.following),
        ),
      )
      .subscribe((appusers: IAppuser[]) => this.appusersSharedCollection.set(appusers));

    this.communityService
      .query()
      .pipe(map((res: HttpResponse<ICommunity[]>) => res.body ?? []))
      .pipe(
        map((communities: ICommunity[]) =>
          this.communityService.addCommunityToCollectionIfMissing<ICommunity>(communities, this.follow?.cfollowed, this.follow?.cfollowing),
        ),
      )
      .subscribe((communities: ICommunity[]) => this.communitiesSharedCollection.set(communities));
  }
}
