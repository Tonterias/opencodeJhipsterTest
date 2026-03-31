import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICeleb, NewCeleb } from '../celeb.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICeleb for edit and NewCelebFormGroupInput for create.
 */
type CelebFormGroupInput = ICeleb | PartialWithRequiredKeyOf<NewCeleb>;

type CelebFormDefaults = Pick<NewCeleb, 'id' | 'appusers'>;

type CelebFormGroupContent = {
  id: FormControl<ICeleb['id'] | NewCeleb['id']>;
  celebName: FormControl<ICeleb['celebName']>;
  appusers: FormControl<ICeleb['appusers']>;
};

export type CelebFormGroup = FormGroup<CelebFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CelebFormService {
  createCelebFormGroup(celeb?: CelebFormGroupInput): CelebFormGroup {
    const celebRawValue = {
      ...this.getFormDefaults(),
      ...(celeb ?? { id: null }),
    };
    return new FormGroup<CelebFormGroupContent>({
      id: new FormControl(
        { value: celebRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      celebName: new FormControl(celebRawValue.celebName, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(40)],
      }),
      appusers: new FormControl(celebRawValue.appusers ?? []),
    });
  }

  getCeleb(form: CelebFormGroup): ICeleb | NewCeleb {
    return form.getRawValue() as ICeleb | NewCeleb;
  }

  resetForm(form: CelebFormGroup, celeb: CelebFormGroupInput): void {
    const celebRawValue = { ...this.getFormDefaults(), ...celeb };
    form.reset({
      ...celebRawValue,
      id: { value: celebRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): CelebFormDefaults {
    return {
      id: null,
      appusers: [],
    };
  }
}
