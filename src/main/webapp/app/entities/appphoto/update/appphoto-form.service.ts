import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAppphoto, NewAppphoto } from '../appphoto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAppphoto for edit and NewAppphotoFormGroupInput for create.
 */
type AppphotoFormGroupInput = IAppphoto | PartialWithRequiredKeyOf<NewAppphoto>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAppphoto | NewAppphoto> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

type AppphotoFormRawValue = FormValueOf<IAppphoto>;

type NewAppphotoFormRawValue = FormValueOf<NewAppphoto>;

type AppphotoFormDefaults = Pick<NewAppphoto, 'id' | 'creationDate'>;

type AppphotoFormGroupContent = {
  id: FormControl<AppphotoFormRawValue['id'] | NewAppphoto['id']>;
  creationDate: FormControl<AppphotoFormRawValue['creationDate']>;
  image: FormControl<AppphotoFormRawValue['image']>;
  imageContentType: FormControl<AppphotoFormRawValue['imageContentType']>;
  appuser: FormControl<AppphotoFormRawValue['appuser']>;
};

export type AppphotoFormGroup = FormGroup<AppphotoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AppphotoFormService {
  createAppphotoFormGroup(appphoto?: AppphotoFormGroupInput): AppphotoFormGroup {
    const appphotoRawValue = this.convertAppphotoToAppphotoRawValue({
      ...this.getFormDefaults(),
      ...(appphoto ?? { id: null }),
    });
    return new FormGroup<AppphotoFormGroupContent>({
      id: new FormControl(
        { value: appphotoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      creationDate: new FormControl(appphotoRawValue.creationDate, {
        validators: [Validators.required],
      }),
      image: new FormControl(appphotoRawValue.image),
      imageContentType: new FormControl(appphotoRawValue.imageContentType),
      appuser: new FormControl(appphotoRawValue.appuser, {
        validators: [Validators.required],
      }),
    });
  }

  getAppphoto(form: AppphotoFormGroup): IAppphoto | NewAppphoto {
    return this.convertAppphotoRawValueToAppphoto(form.getRawValue() as AppphotoFormRawValue | NewAppphotoFormRawValue);
  }

  resetForm(form: AppphotoFormGroup, appphoto: AppphotoFormGroupInput): void {
    const appphotoRawValue = this.convertAppphotoToAppphotoRawValue({ ...this.getFormDefaults(), ...appphoto });
    form.reset({
      ...appphotoRawValue,
      id: { value: appphotoRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): AppphotoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
    };
  }

  private convertAppphotoRawValueToAppphoto(rawAppphoto: AppphotoFormRawValue | NewAppphotoFormRawValue): IAppphoto | NewAppphoto {
    return {
      ...rawAppphoto,
      creationDate: dayjs(rawAppphoto.creationDate, DATE_TIME_FORMAT),
    };
  }

  private convertAppphotoToAppphotoRawValue(
    appphoto: IAppphoto | (Partial<NewAppphoto> & AppphotoFormDefaults),
  ): AppphotoFormRawValue | PartialWithRequiredKeyOf<NewAppphotoFormRawValue> {
    return {
      ...appphoto,
      creationDate: appphoto.creationDate ? appphoto.creationDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
