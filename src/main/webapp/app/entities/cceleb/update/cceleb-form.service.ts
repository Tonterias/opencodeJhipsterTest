import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICceleb, NewCceleb } from '../cceleb.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICceleb for edit and NewCcelebFormGroupInput for create.
 */
type CcelebFormGroupInput = ICceleb | PartialWithRequiredKeyOf<NewCceleb>;

type CcelebFormDefaults = Pick<NewCceleb, 'id' | 'communities'>;

type CcelebFormGroupContent = {
  id: FormControl<ICceleb['id'] | NewCceleb['id']>;
  celebName: FormControl<ICceleb['celebName']>;
  communities: FormControl<ICceleb['communities']>;
};

export type CcelebFormGroup = FormGroup<CcelebFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CcelebFormService {
  createCcelebFormGroup(cceleb?: CcelebFormGroupInput): CcelebFormGroup {
    const ccelebRawValue = {
      ...this.getFormDefaults(),
      ...(cceleb ?? { id: null }),
    };
    return new FormGroup<CcelebFormGroupContent>({
      id: new FormControl(
        { value: ccelebRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      celebName: new FormControl(ccelebRawValue.celebName, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(40)],
      }),
      communities: new FormControl(ccelebRawValue.communities ?? []),
    });
  }

  getCceleb(form: CcelebFormGroup): ICceleb | NewCceleb {
    return form.getRawValue() as ICceleb | NewCceleb;
  }

  resetForm(form: CcelebFormGroup, cceleb: CcelebFormGroupInput): void {
    const ccelebRawValue = { ...this.getFormDefaults(), ...cceleb };
    form.reset({
      ...ccelebRawValue,
      id: { value: ccelebRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): CcelebFormDefaults {
    return {
      id: null,
      communities: [],
    };
  }
}
