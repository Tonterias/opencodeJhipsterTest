import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICactivity, NewCactivity } from '../cactivity.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICactivity for edit and NewCactivityFormGroupInput for create.
 */
type CactivityFormGroupInput = ICactivity | PartialWithRequiredKeyOf<NewCactivity>;

type CactivityFormDefaults = Pick<NewCactivity, 'id' | 'communities'>;

type CactivityFormGroupContent = {
  id: FormControl<ICactivity['id'] | NewCactivity['id']>;
  activityName: FormControl<ICactivity['activityName']>;
  communities: FormControl<ICactivity['communities']>;
};

export type CactivityFormGroup = FormGroup<CactivityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CactivityFormService {
  createCactivityFormGroup(cactivity?: CactivityFormGroupInput): CactivityFormGroup {
    const cactivityRawValue = {
      ...this.getFormDefaults(),
      ...(cactivity ?? { id: null }),
    };
    return new FormGroup<CactivityFormGroupContent>({
      id: new FormControl(
        { value: cactivityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      activityName: new FormControl(cactivityRawValue.activityName, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(40)],
      }),
      communities: new FormControl(cactivityRawValue.communities ?? []),
    });
  }

  getCactivity(form: CactivityFormGroup): ICactivity | NewCactivity {
    return form.getRawValue() as ICactivity | NewCactivity;
  }

  resetForm(form: CactivityFormGroup, cactivity: CactivityFormGroupInput): void {
    const cactivityRawValue = { ...this.getFormDefaults(), ...cactivity };
    form.reset({
      ...cactivityRawValue,
      id: { value: cactivityRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): CactivityFormDefaults {
    return {
      id: null,
      communities: [],
    };
  }
}
