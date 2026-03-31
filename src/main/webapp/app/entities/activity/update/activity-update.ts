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
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { IActivity } from '../activity.model';
import { ActivityService } from '../service/activity.service';

import { ActivityFormGroup, ActivityFormService } from './activity-form.service';

@Component({
  selector: 'jhi-activity-update',
  templateUrl: './activity-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class ActivityUpdate implements OnInit {
  readonly isSaving = signal(false);
  activity: IActivity | null = null;

  appusersSharedCollection = signal<IAppuser[]>([]);

  protected activityService = inject(ActivityService);
  protected activityFormService = inject(ActivityFormService);
  protected appuserService = inject(AppuserService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ActivityFormGroup = this.activityFormService.createActivityFormGroup();

  compareAppuser = (o1: IAppuser | null, o2: IAppuser | null): boolean => this.appuserService.compareAppuser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activity }) => {
      this.activity = activity;
      if (activity) {
        this.updateForm(activity);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const activity = this.activityFormService.getActivity(this.editForm);
    if (activity.id === null) {
      this.subscribeToSaveResponse(this.activityService.create(activity));
    } else {
      this.subscribeToSaveResponse(this.activityService.update(activity));
    }
  }

  protected subscribeToSaveResponse(result: Observable<IActivity | null>): void {
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

  protected updateForm(activity: IActivity): void {
    this.activity = activity;
    this.activityFormService.resetForm(this.editForm, activity);

    this.appusersSharedCollection.update(appusers =>
      this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, ...(activity.appusers ?? [])),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.appuserService
      .query()
      .pipe(map((res: HttpResponse<IAppuser[]>) => res.body ?? []))
      .pipe(
        map((appusers: IAppuser[]) =>
          this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, ...(this.activity?.appusers ?? [])),
        ),
      )
      .subscribe((appusers: IAppuser[]) => this.appusersSharedCollection.set(appusers));
  }
}
