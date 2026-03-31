import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IUrllink, NewUrllink } from '../urllink.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUrllink for edit and NewUrllinkFormGroupInput for create.
 */
type UrllinkFormGroupInput = IUrllink | PartialWithRequiredKeyOf<NewUrllink>;

type UrllinkFormDefaults = Pick<NewUrllink, 'id'>;

type UrllinkFormGroupContent = {
  id: FormControl<IUrllink['id'] | NewUrllink['id']>;
  linkText: FormControl<IUrllink['linkText']>;
  linkURL: FormControl<IUrllink['linkURL']>;
};

export type UrllinkFormGroup = FormGroup<UrllinkFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UrllinkFormService {
  createUrllinkFormGroup(urllink?: UrllinkFormGroupInput): UrllinkFormGroup {
    const urllinkRawValue = {
      ...this.getFormDefaults(),
      ...(urllink ?? { id: null }),
    };
    return new FormGroup<UrllinkFormGroupContent>({
      id: new FormControl(
        { value: urllinkRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      linkText: new FormControl(urllinkRawValue.linkText, {
        validators: [Validators.required],
      }),
      linkURL: new FormControl(urllinkRawValue.linkURL, {
        validators: [Validators.required],
      }),
    });
  }

  getUrllink(form: UrllinkFormGroup): IUrllink | NewUrllink {
    return form.getRawValue() as IUrllink | NewUrllink;
  }

  resetForm(form: UrllinkFormGroup, urllink: UrllinkFormGroupInput): void {
    const urllinkRawValue = { ...this.getFormDefaults(), ...urllink };
    form.reset({
      ...urllinkRawValue,
      id: { value: urllinkRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): UrllinkFormDefaults {
    return {
      id: null,
    };
  }
}
