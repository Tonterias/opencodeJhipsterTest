import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IActivity, NewActivity } from '../activity.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IActivity for edit and NewActivityFormGroupInput for create.
 */
type ActivityFormGroupInput = IActivity | PartialWithRequiredKeyOf<NewActivity>;

type ActivityFormDefaults = Pick<NewActivity, 'id' | 'appusers'>;

type ActivityFormGroupContent = {
  id: FormControl<IActivity['id'] | NewActivity['id']>;
  activityName: FormControl<IActivity['activityName']>;
  appusers: FormControl<IActivity['appusers']>;
};

export type ActivityFormGroup = FormGroup<ActivityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ActivityFormService {
  createActivityFormGroup(activity?: ActivityFormGroupInput): ActivityFormGroup {
    const activityRawValue = {
      ...this.getFormDefaults(),
      ...(activity ?? { id: null }),
    };
    return new FormGroup<ActivityFormGroupContent>({
      id: new FormControl(
        { value: activityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      activityName: new FormControl(activityRawValue.activityName, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(40)],
      }),
      appusers: new FormControl(activityRawValue.appusers ?? []),
    });
  }

  getActivity(form: ActivityFormGroup): IActivity | NewActivity {
    return form.getRawValue() as IActivity | NewActivity;
  }

  resetForm(form: ActivityFormGroup, activity: ActivityFormGroupInput): void {
    const activityRawValue = { ...this.getFormDefaults(), ...activity };
    form.reset({
      ...activityRawValue,
      id: { value: activityRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): ActivityFormDefaults {
    return {
      id: null,
      appusers: [],
    };
  }
}
