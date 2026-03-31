import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INotification, NewNotification } from '../notification.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INotification for edit and NewNotificationFormGroupInput for create.
 */
type NotificationFormGroupInput = INotification | PartialWithRequiredKeyOf<NewNotification>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends INotification | NewNotification> = Omit<T, 'creationDate' | 'notificationDate'> & {
  creationDate?: string | null;
  notificationDate?: string | null;
};

type NotificationFormRawValue = FormValueOf<INotification>;

type NewNotificationFormRawValue = FormValueOf<NewNotification>;

type NotificationFormDefaults = Pick<NewNotification, 'id' | 'creationDate' | 'notificationDate' | 'isDelivered'>;

type NotificationFormGroupContent = {
  id: FormControl<NotificationFormRawValue['id'] | NewNotification['id']>;
  creationDate: FormControl<NotificationFormRawValue['creationDate']>;
  notificationDate: FormControl<NotificationFormRawValue['notificationDate']>;
  notificationReason: FormControl<NotificationFormRawValue['notificationReason']>;
  notificationText: FormControl<NotificationFormRawValue['notificationText']>;
  isDelivered: FormControl<NotificationFormRawValue['isDelivered']>;
  appuser: FormControl<NotificationFormRawValue['appuser']>;
};

export type NotificationFormGroup = FormGroup<NotificationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NotificationFormService {
  createNotificationFormGroup(notification?: NotificationFormGroupInput): NotificationFormGroup {
    const notificationRawValue = this.convertNotificationToNotificationRawValue({
      ...this.getFormDefaults(),
      ...(notification ?? { id: null }),
    });
    return new FormGroup<NotificationFormGroupContent>({
      id: new FormControl(
        { value: notificationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      creationDate: new FormControl(notificationRawValue.creationDate, {
        validators: [Validators.required],
      }),
      notificationDate: new FormControl(notificationRawValue.notificationDate),
      notificationReason: new FormControl(notificationRawValue.notificationReason, {
        validators: [Validators.required],
      }),
      notificationText: new FormControl(notificationRawValue.notificationText, {
        validators: [Validators.minLength(2), Validators.maxLength(100)],
      }),
      isDelivered: new FormControl(notificationRawValue.isDelivered),
      appuser: new FormControl(notificationRawValue.appuser, {
        validators: [Validators.required],
      }),
    });
  }

  getNotification(form: NotificationFormGroup): INotification | NewNotification {
    return this.convertNotificationRawValueToNotification(form.getRawValue() as NotificationFormRawValue | NewNotificationFormRawValue);
  }

  resetForm(form: NotificationFormGroup, notification: NotificationFormGroupInput): void {
    const notificationRawValue = this.convertNotificationToNotificationRawValue({ ...this.getFormDefaults(), ...notification });
    form.reset({
      ...notificationRawValue,
      id: { value: notificationRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): NotificationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
      notificationDate: currentTime,
      isDelivered: false,
    };
  }

  private convertNotificationRawValueToNotification(
    rawNotification: NotificationFormRawValue | NewNotificationFormRawValue,
  ): INotification | NewNotification {
    return {
      ...rawNotification,
      creationDate: dayjs(rawNotification.creationDate, DATE_TIME_FORMAT),
      notificationDate: dayjs(rawNotification.notificationDate, DATE_TIME_FORMAT),
    };
  }

  private convertNotificationToNotificationRawValue(
    notification: INotification | (Partial<NewNotification> & NotificationFormDefaults),
  ): NotificationFormRawValue | PartialWithRequiredKeyOf<NewNotificationFormRawValue> {
    return {
      ...notification,
      creationDate: notification.creationDate ? notification.creationDate.format(DATE_TIME_FORMAT) : undefined,
      notificationDate: notification.notificationDate ? notification.notificationDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
