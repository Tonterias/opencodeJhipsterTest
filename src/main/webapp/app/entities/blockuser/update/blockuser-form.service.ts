import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBlockuser, NewBlockuser } from '../blockuser.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBlockuser for edit and NewBlockuserFormGroupInput for create.
 */
type BlockuserFormGroupInput = IBlockuser | PartialWithRequiredKeyOf<NewBlockuser>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IBlockuser | NewBlockuser> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

type BlockuserFormRawValue = FormValueOf<IBlockuser>;

type NewBlockuserFormRawValue = FormValueOf<NewBlockuser>;

type BlockuserFormDefaults = Pick<NewBlockuser, 'id' | 'creationDate'>;

type BlockuserFormGroupContent = {
  id: FormControl<BlockuserFormRawValue['id'] | NewBlockuser['id']>;
  creationDate: FormControl<BlockuserFormRawValue['creationDate']>;
  blockeduser: FormControl<BlockuserFormRawValue['blockeduser']>;
  blockinguser: FormControl<BlockuserFormRawValue['blockinguser']>;
  cblockeduser: FormControl<BlockuserFormRawValue['cblockeduser']>;
  cblockinguser: FormControl<BlockuserFormRawValue['cblockinguser']>;
};

export type BlockuserFormGroup = FormGroup<BlockuserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BlockuserFormService {
  createBlockuserFormGroup(blockuser?: BlockuserFormGroupInput): BlockuserFormGroup {
    const blockuserRawValue = this.convertBlockuserToBlockuserRawValue({
      ...this.getFormDefaults(),
      ...(blockuser ?? { id: null }),
    });
    return new FormGroup<BlockuserFormGroupContent>({
      id: new FormControl(
        { value: blockuserRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      creationDate: new FormControl(blockuserRawValue.creationDate),
      blockeduser: new FormControl(blockuserRawValue.blockeduser),
      blockinguser: new FormControl(blockuserRawValue.blockinguser),
      cblockeduser: new FormControl(blockuserRawValue.cblockeduser),
      cblockinguser: new FormControl(blockuserRawValue.cblockinguser),
    });
  }

  getBlockuser(form: BlockuserFormGroup): IBlockuser | NewBlockuser {
    return this.convertBlockuserRawValueToBlockuser(form.getRawValue() as BlockuserFormRawValue | NewBlockuserFormRawValue);
  }

  resetForm(form: BlockuserFormGroup, blockuser: BlockuserFormGroupInput): void {
    const blockuserRawValue = this.convertBlockuserToBlockuserRawValue({ ...this.getFormDefaults(), ...blockuser });
    form.reset({
      ...blockuserRawValue,
      id: { value: blockuserRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): BlockuserFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
    };
  }

  private convertBlockuserRawValueToBlockuser(rawBlockuser: BlockuserFormRawValue | NewBlockuserFormRawValue): IBlockuser | NewBlockuser {
    return {
      ...rawBlockuser,
      creationDate: dayjs(rawBlockuser.creationDate, DATE_TIME_FORMAT),
    };
  }

  private convertBlockuserToBlockuserRawValue(
    blockuser: IBlockuser | (Partial<NewBlockuser> & BlockuserFormDefaults),
  ): BlockuserFormRawValue | PartialWithRequiredKeyOf<NewBlockuserFormRawValue> {
    return {
      ...blockuser,
      creationDate: blockuser.creationDate ? blockuser.creationDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
