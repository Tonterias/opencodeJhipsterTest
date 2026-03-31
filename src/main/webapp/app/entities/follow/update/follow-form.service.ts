import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFollow, NewFollow } from '../follow.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFollow for edit and NewFollowFormGroupInput for create.
 */
type FollowFormGroupInput = IFollow | PartialWithRequiredKeyOf<NewFollow>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFollow | NewFollow> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

type FollowFormRawValue = FormValueOf<IFollow>;

type NewFollowFormRawValue = FormValueOf<NewFollow>;

type FollowFormDefaults = Pick<NewFollow, 'id' | 'creationDate'>;

type FollowFormGroupContent = {
  id: FormControl<FollowFormRawValue['id'] | NewFollow['id']>;
  creationDate: FormControl<FollowFormRawValue['creationDate']>;
  followed: FormControl<FollowFormRawValue['followed']>;
  following: FormControl<FollowFormRawValue['following']>;
  cfollowed: FormControl<FollowFormRawValue['cfollowed']>;
  cfollowing: FormControl<FollowFormRawValue['cfollowing']>;
};

export type FollowFormGroup = FormGroup<FollowFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FollowFormService {
  createFollowFormGroup(follow?: FollowFormGroupInput): FollowFormGroup {
    const followRawValue = this.convertFollowToFollowRawValue({
      ...this.getFormDefaults(),
      ...(follow ?? { id: null }),
    });
    return new FormGroup<FollowFormGroupContent>({
      id: new FormControl(
        { value: followRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      creationDate: new FormControl(followRawValue.creationDate),
      followed: new FormControl(followRawValue.followed),
      following: new FormControl(followRawValue.following),
      cfollowed: new FormControl(followRawValue.cfollowed),
      cfollowing: new FormControl(followRawValue.cfollowing),
    });
  }

  getFollow(form: FollowFormGroup): IFollow | NewFollow {
    return this.convertFollowRawValueToFollow(form.getRawValue() as FollowFormRawValue | NewFollowFormRawValue);
  }

  resetForm(form: FollowFormGroup, follow: FollowFormGroupInput): void {
    const followRawValue = this.convertFollowToFollowRawValue({ ...this.getFormDefaults(), ...follow });
    form.reset({
      ...followRawValue,
      id: { value: followRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): FollowFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
    };
  }

  private convertFollowRawValueToFollow(rawFollow: FollowFormRawValue | NewFollowFormRawValue): IFollow | NewFollow {
    return {
      ...rawFollow,
      creationDate: dayjs(rawFollow.creationDate, DATE_TIME_FORMAT),
    };
  }

  private convertFollowToFollowRawValue(
    follow: IFollow | (Partial<NewFollow> & FollowFormDefaults),
  ): FollowFormRawValue | PartialWithRequiredKeyOf<NewFollowFormRawValue> {
    return {
      ...follow,
      creationDate: follow.creationDate ? follow.creationDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
