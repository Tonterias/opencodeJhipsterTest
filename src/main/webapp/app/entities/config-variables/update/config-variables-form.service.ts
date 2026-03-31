import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IConfigVariables, NewConfigVariables } from '../config-variables.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IConfigVariables for edit and NewConfigVariablesFormGroupInput for create.
 */
type ConfigVariablesFormGroupInput = IConfigVariables | PartialWithRequiredKeyOf<NewConfigVariables>;

type ConfigVariablesFormDefaults = Pick<NewConfigVariables, 'id' | 'configVarBoolean16' | 'configVarBoolean17' | 'configVarBoolean18'>;

type ConfigVariablesFormGroupContent = {
  id: FormControl<IConfigVariables['id'] | NewConfigVariables['id']>;
  configVarLong1: FormControl<IConfigVariables['configVarLong1']>;
  configVarLong2: FormControl<IConfigVariables['configVarLong2']>;
  configVarLong3: FormControl<IConfigVariables['configVarLong3']>;
  configVarLong4: FormControl<IConfigVariables['configVarLong4']>;
  configVarLong5: FormControl<IConfigVariables['configVarLong5']>;
  configVarLong6: FormControl<IConfigVariables['configVarLong6']>;
  configVarLong7: FormControl<IConfigVariables['configVarLong7']>;
  configVarLong8: FormControl<IConfigVariables['configVarLong8']>;
  configVarLong9: FormControl<IConfigVariables['configVarLong9']>;
  configVarLong10: FormControl<IConfigVariables['configVarLong10']>;
  configVarLong11: FormControl<IConfigVariables['configVarLong11']>;
  configVarLong12: FormControl<IConfigVariables['configVarLong12']>;
  configVarLong13: FormControl<IConfigVariables['configVarLong13']>;
  configVarLong14: FormControl<IConfigVariables['configVarLong14']>;
  configVarLong15: FormControl<IConfigVariables['configVarLong15']>;
  configVarBoolean16: FormControl<IConfigVariables['configVarBoolean16']>;
  configVarBoolean17: FormControl<IConfigVariables['configVarBoolean17']>;
  configVarBoolean18: FormControl<IConfigVariables['configVarBoolean18']>;
  configVarString19: FormControl<IConfigVariables['configVarString19']>;
  configVarString20: FormControl<IConfigVariables['configVarString20']>;
};

export type ConfigVariablesFormGroup = FormGroup<ConfigVariablesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ConfigVariablesFormService {
  createConfigVariablesFormGroup(configVariables?: ConfigVariablesFormGroupInput): ConfigVariablesFormGroup {
    const configVariablesRawValue = {
      ...this.getFormDefaults(),
      ...(configVariables ?? { id: null }),
    };
    return new FormGroup<ConfigVariablesFormGroupContent>({
      id: new FormControl(
        { value: configVariablesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      configVarLong1: new FormControl(configVariablesRawValue.configVarLong1),
      configVarLong2: new FormControl(configVariablesRawValue.configVarLong2),
      configVarLong3: new FormControl(configVariablesRawValue.configVarLong3),
      configVarLong4: new FormControl(configVariablesRawValue.configVarLong4),
      configVarLong5: new FormControl(configVariablesRawValue.configVarLong5),
      configVarLong6: new FormControl(configVariablesRawValue.configVarLong6),
      configVarLong7: new FormControl(configVariablesRawValue.configVarLong7),
      configVarLong8: new FormControl(configVariablesRawValue.configVarLong8),
      configVarLong9: new FormControl(configVariablesRawValue.configVarLong9),
      configVarLong10: new FormControl(configVariablesRawValue.configVarLong10),
      configVarLong11: new FormControl(configVariablesRawValue.configVarLong11),
      configVarLong12: new FormControl(configVariablesRawValue.configVarLong12),
      configVarLong13: new FormControl(configVariablesRawValue.configVarLong13),
      configVarLong14: new FormControl(configVariablesRawValue.configVarLong14),
      configVarLong15: new FormControl(configVariablesRawValue.configVarLong15),
      configVarBoolean16: new FormControl(configVariablesRawValue.configVarBoolean16),
      configVarBoolean17: new FormControl(configVariablesRawValue.configVarBoolean17),
      configVarBoolean18: new FormControl(configVariablesRawValue.configVarBoolean18),
      configVarString19: new FormControl(configVariablesRawValue.configVarString19),
      configVarString20: new FormControl(configVariablesRawValue.configVarString20),
    });
  }

  getConfigVariables(form: ConfigVariablesFormGroup): IConfigVariables | NewConfigVariables {
    return form.getRawValue() as IConfigVariables | NewConfigVariables;
  }

  resetForm(form: ConfigVariablesFormGroup, configVariables: ConfigVariablesFormGroupInput): void {
    const configVariablesRawValue = { ...this.getFormDefaults(), ...configVariables };
    form.reset({
      ...configVariablesRawValue,
      id: { value: configVariablesRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): ConfigVariablesFormDefaults {
    return {
      id: null,
      configVarBoolean16: false,
      configVarBoolean17: false,
      configVarBoolean18: false,
    };
  }
}
