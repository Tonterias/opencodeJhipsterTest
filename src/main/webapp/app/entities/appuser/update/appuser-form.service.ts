import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAppuser, NewAppuser } from '../appuser.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAppuser for edit and NewAppuserFormGroupInput for create.
 */
type AppuserFormGroupInput = IAppuser | PartialWithRequiredKeyOf<NewAppuser>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAppuser | NewAppuser> = Omit<T, 'creationDate' | 'birthdate'> & {
  creationDate?: string | null;
  birthdate?: string | null;
};

type AppuserFormRawValue = FormValueOf<IAppuser>;

type NewAppuserFormRawValue = FormValueOf<NewAppuser>;

type AppuserFormDefaults = Pick<NewAppuser, 'id' | 'creationDate' | 'birthdate' | 'interests' | 'activities' | 'celebs'>;

type AppuserFormGroupContent = {
  id: FormControl<AppuserFormRawValue['id'] | NewAppuser['id']>;
  creationDate: FormControl<AppuserFormRawValue['creationDate']>;
  bio: FormControl<AppuserFormRawValue['bio']>;
  facebook: FormControl<AppuserFormRawValue['facebook']>;
  twitter: FormControl<AppuserFormRawValue['twitter']>;
  linkedin: FormControl<AppuserFormRawValue['linkedin']>;
  instagram: FormControl<AppuserFormRawValue['instagram']>;
  birthdate: FormControl<AppuserFormRawValue['birthdate']>;
  user: FormControl<AppuserFormRawValue['user']>;
  interests: FormControl<AppuserFormRawValue['interests']>;
  activities: FormControl<AppuserFormRawValue['activities']>;
  celebs: FormControl<AppuserFormRawValue['celebs']>;
};

export type AppuserFormGroup = FormGroup<AppuserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AppuserFormService {
  createAppuserFormGroup(appuser?: AppuserFormGroupInput): AppuserFormGroup {
    const appuserRawValue = this.convertAppuserToAppuserRawValue({
      ...this.getFormDefaults(),
      ...(appuser ?? { id: null }),
    });
    return new FormGroup<AppuserFormGroupContent>({
      id: new FormControl(
        { value: appuserRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      creationDate: new FormControl(appuserRawValue.creationDate, {
        validators: [Validators.required],
      }),
      bio: new FormControl(appuserRawValue.bio, {
        validators: [Validators.maxLength(7500)],
      }),
      facebook: new FormControl(appuserRawValue.facebook, {
        validators: [Validators.maxLength(50)],
      }),
      twitter: new FormControl(appuserRawValue.twitter, {
        validators: [Validators.maxLength(50)],
      }),
      linkedin: new FormControl(appuserRawValue.linkedin, {
        validators: [Validators.maxLength(50)],
      }),
      instagram: new FormControl(appuserRawValue.instagram, {
        validators: [Validators.maxLength(50)],
      }),
      birthdate: new FormControl(appuserRawValue.birthdate),
      user: new FormControl(appuserRawValue.user, {
        validators: [Validators.required],
      }),
      interests: new FormControl(appuserRawValue.interests ?? []),
      activities: new FormControl(appuserRawValue.activities ?? []),
      celebs: new FormControl(appuserRawValue.celebs ?? []),
    });
  }

  getAppuser(form: AppuserFormGroup): IAppuser | NewAppuser {
    return this.convertAppuserRawValueToAppuser(form.getRawValue() as AppuserFormRawValue | NewAppuserFormRawValue);
  }

  resetForm(form: AppuserFormGroup, appuser: AppuserFormGroupInput): void {
    const appuserRawValue = this.convertAppuserToAppuserRawValue({ ...this.getFormDefaults(), ...appuser });
    form.reset({
      ...appuserRawValue,
      id: { value: appuserRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): AppuserFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
      birthdate: currentTime,
      interests: [],
      activities: [],
      celebs: [],
    };
  }

  private convertAppuserRawValueToAppuser(rawAppuser: AppuserFormRawValue | NewAppuserFormRawValue): IAppuser | NewAppuser {
    return {
      ...rawAppuser,
      creationDate: dayjs(rawAppuser.creationDate, DATE_TIME_FORMAT),
      birthdate: dayjs(rawAppuser.birthdate, DATE_TIME_FORMAT),
    };
  }

  private convertAppuserToAppuserRawValue(
    appuser: IAppuser | (Partial<NewAppuser> & AppuserFormDefaults),
  ): AppuserFormRawValue | PartialWithRequiredKeyOf<NewAppuserFormRawValue> {
    return {
      ...appuser,
      creationDate: appuser.creationDate ? appuser.creationDate.format(DATE_TIME_FORMAT) : undefined,
      birthdate: appuser.birthdate ? appuser.birthdate.format(DATE_TIME_FORMAT) : undefined,
      interests: appuser.interests ?? [],
      activities: appuser.activities ?? [],
      celebs: appuser.celebs ?? [],
    };
  }
}
