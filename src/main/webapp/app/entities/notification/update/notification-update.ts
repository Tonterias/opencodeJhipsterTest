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
import { NotificationReason } from 'app/entities/enumerations/notification-reason.model';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';
import { INotification } from '../notification.model';
import { NotificationService } from '../service/notification.service';

import { NotificationFormGroup, NotificationFormService } from './notification-form.service';

@Component({
  selector: 'jhi-notification-update',
  templateUrl: './notification-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, ReactiveFormsModule],
})
export class NotificationUpdate implements OnInit {
  readonly isSaving = signal(false);
  notification: INotification | null = null;
  notificationReasonValues = Object.keys(NotificationReason);

  appusersSharedCollection = signal<IAppuser[]>([]);

  protected notificationService = inject(NotificationService);
  protected notificationFormService = inject(NotificationFormService);
  protected appuserService = inject(AppuserService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: NotificationFormGroup = this.notificationFormService.createNotificationFormGroup();

  compareAppuser = (o1: IAppuser | null, o2: IAppuser | null): boolean => this.appuserService.compareAppuser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notification }) => {
      this.notification = notification;
      if (notification) {
        this.updateForm(notification);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    const notification = this.notificationFormService.getNotification(this.editForm);
    if (notification.id === null) {
      this.subscribeToSaveResponse(this.notificationService.create(notification));
    } else {
      this.subscribeToSaveResponse(this.notificationService.update(notification));
    }
  }

  protected subscribeToSaveResponse(result: Observable<INotification | null>): void {
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

  protected updateForm(notification: INotification): void {
    this.notification = notification;
    this.notificationFormService.resetForm(this.editForm, notification);

    this.appusersSharedCollection.update(appusers =>
      this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, notification.appuser),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.appuserService
      .query()
      .pipe(map((res: HttpResponse<IAppuser[]>) => res.body ?? []))
      .pipe(
        map((appusers: IAppuser[]) => this.appuserService.addAppuserToCollectionIfMissing<IAppuser>(appusers, this.notification?.appuser)),
      )
      .subscribe((appusers: IAppuser[]) => this.appusersSharedCollection.set(appusers));
  }
}
