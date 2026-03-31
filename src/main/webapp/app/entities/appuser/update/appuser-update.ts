import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IActivity } from 'app/entities/activity/activity.model';
import { ActivityService } from 'app/entities/activity/service/activity.service';
import { ICeleb } from 'app/entities/celeb/celeb.model';
import { IInterest } from 'app/entities/interest/interest.model';
import { UserService } from 'app/entities/user/service/user.service';
import { IUser } from 'app/entities/user/user.model';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';

import { IAppuser } from '../appuser.model';
import { AppuserService } from '../service/appuser.service';

import { AppuserFormGroup, AppuserFormService } from './appuser-form.service';
import { InterestService } from 'app/entities/interest/service/interest.service';
import { CelebService } from 'app/entities/celeb/service/celeb.service';

@Component({
  selector: 'jhi-appuser-update',
  templateUrl: './appuser-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class AppuserUpdate implements OnInit {
  readonly isSaving = signal(false);
  appuser: IAppuser | null = null;

  usersSharedCollection = signal<IUser[]>([]);
  interestsSharedCollection = signal<IInterest[]>([]);
  activitiesSharedCollection = signal<IActivity[]>([]);
  celebsSharedCollection = signal<ICeleb[]>([]);

  protected appuserService = inject(AppuserService);
  protected appuserFormService = inject(AppuserFormService);
  protected userService = inject(UserService);
  protected interestService = inject(InterestService);
  protected activityService = inject(ActivityService);
  protected celebService = inject(CelebService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AppuserFormGroup = this.appuserFormService.createAppuserFormGroup();

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareInterest = (o1: IInterest | null, o2: IInterest | null): boolean => this.interestService.compareInterest(o1, o2);

  compareActivity = (o1: IActivity | null, o2: IActivity | null): boolean => this.activityService.compareActivity(o1, o2);

  compareCeleb = (o1: ICeleb | null, o2: ICeleb | null): boolean => this.celebService.compareCeleb(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appuser }) => {
      this.appuser = appuser;
      if (appuser) {
        this.updateForm(appuser);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const appuser = this.appuserFormService.getAppuser(this.editForm);
    if (appuser.id === null) {
      this.subscribeToSaveResponse(this.appuserService.create(appuser));
    } else {
      this.subscribeToSaveResponse(this.appuserService.update(appuser));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IAppuser | null>): void {
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

  protected updateForm(appuser: IAppuser): void {
    this.appuser = appuser;
    this.appuserFormService.resetForm(this.editForm, appuser);

    this.usersSharedCollection.update(users => this.userService.addUserToCollectionIfMissing<IUser>(users, appuser.user));
    this.interestsSharedCollection.update(interests =>
      this.interestService.addInterestToCollectionIfMissing<IInterest>(interests, ...(appuser.interests ?? [])),
    );
    this.activitiesSharedCollection.update(activities =>
      this.activityService.addActivityToCollectionIfMissing<IActivity>(activities, ...(appuser.activities ?? [])),
    );
    this.celebsSharedCollection.update(celebs =>
      this.celebService.addCelebToCollectionIfMissing<ICeleb>(celebs, ...(appuser.celebs ?? [])),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.appuser?.user)))
      .subscribe((users: IUser[]) => this.usersSharedCollection.set(users));

    this.interestService
      .query()
      .pipe(map((res: HttpResponse<IInterest[]>) => res.body ?? []))
      .pipe(
        map((interests: IInterest[]) =>
          this.interestService.addInterestToCollectionIfMissing<IInterest>(interests, ...(this.appuser?.interests ?? [])),
        ),
      )
      .subscribe((interests: IInterest[]) => this.interestsSharedCollection.set(interests));

    this.activityService
      .query()
      .pipe(map((res: HttpResponse<IActivity[]>) => res.body ?? []))
      .pipe(
        map((activities: IActivity[]) =>
          this.activityService.addActivityToCollectionIfMissing<IActivity>(activities, ...(this.appuser?.activities ?? [])),
        ),
      )
      .subscribe((activities: IActivity[]) => this.activitiesSharedCollection.set(activities));

    this.celebService
      .query()
      .pipe(map((res: HttpResponse<ICeleb[]>) => res.body ?? []))
      .pipe(map((celebs: ICeleb[]) => this.celebService.addCelebToCollectionIfMissing<ICeleb>(celebs, ...(this.appuser?.celebs ?? []))))
      .subscribe((celebs: ICeleb[]) => this.celebsSharedCollection.set(celebs));
  }
}
