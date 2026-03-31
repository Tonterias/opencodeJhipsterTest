import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICinterest, NewCinterest } from '../cinterest.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICinterest for edit and NewCinterestFormGroupInput for create.
 */
type CinterestFormGroupInput = ICinterest | PartialWithRequiredKeyOf<NewCinterest>;

type CinterestFormDefaults = Pick<NewCinterest, 'id' | 'communities'>;

type CinterestFormGroupContent = {
  id: FormControl<ICinterest['id'] | NewCinterest['id']>;
  interestName: FormControl<ICinterest['interestName']>;
  communities: FormControl<ICinterest['communities']>;
};

export type CinterestFormGroup = FormGroup<CinterestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CinterestFormService {
  createCinterestFormGroup(cinterest?: CinterestFormGroupInput): CinterestFormGroup {
    const cinterestRawValue = {
      ...this.getFormDefaults(),
      ...(cinterest ?? { id: null }),
    };
    return new FormGroup<CinterestFormGroupContent>({
      id: new FormControl(
        { value: cinterestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      interestName: new FormControl(cinterestRawValue.interestName, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(40)],
      }),
      communities: new FormControl(cinterestRawValue.communities ?? []),
    });
  }

  getCinterest(form: CinterestFormGroup): ICinterest | NewCinterest {
    return form.getRawValue() as ICinterest | NewCinterest;
  }

  resetForm(form: CinterestFormGroup, cinterest: CinterestFormGroupInput): void {
    const cinterestRawValue = { ...this.getFormDefaults(), ...cinterest };
    form.reset({
      ...cinterestRawValue,
      id: { value: cinterestRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): CinterestFormDefaults {
    return {
      id: null,
      communities: [],
    };
  }
}
