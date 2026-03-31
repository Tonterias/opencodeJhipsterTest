import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IInterest, NewInterest } from '../interest.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInterest for edit and NewInterestFormGroupInput for create.
 */
type InterestFormGroupInput = IInterest | PartialWithRequiredKeyOf<NewInterest>;

type InterestFormDefaults = Pick<NewInterest, 'id' | 'appusers'>;

type InterestFormGroupContent = {
  id: FormControl<IInterest['id'] | NewInterest['id']>;
  interestName: FormControl<IInterest['interestName']>;
  appusers: FormControl<IInterest['appusers']>;
};

export type InterestFormGroup = FormGroup<InterestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InterestFormService {
  createInterestFormGroup(interest?: InterestFormGroupInput): InterestFormGroup {
    const interestRawValue = {
      ...this.getFormDefaults(),
      ...(interest ?? { id: null }),
    };
    return new FormGroup<InterestFormGroupContent>({
      id: new FormControl(
        { value: interestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      interestName: new FormControl(interestRawValue.interestName, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(40)],
      }),
      appusers: new FormControl(interestRawValue.appusers ?? []),
    });
  }

  getInterest(form: InterestFormGroup): IInterest | NewInterest {
    return form.getRawValue() as IInterest | NewInterest;
  }

  resetForm(form: InterestFormGroup, interest: InterestFormGroupInput): void {
    const interestRawValue = { ...this.getFormDefaults(), ...interest };
    form.reset({
      ...interestRawValue,
      id: { value: interestRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): InterestFormDefaults {
    return {
      id: null,
      appusers: [],
    };
  }
}
