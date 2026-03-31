import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICommunity, NewCommunity } from '../community.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICommunity for edit and NewCommunityFormGroupInput for create.
 */
type CommunityFormGroupInput = ICommunity | PartialWithRequiredKeyOf<NewCommunity>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICommunity | NewCommunity> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

type CommunityFormRawValue = FormValueOf<ICommunity>;

type NewCommunityFormRawValue = FormValueOf<NewCommunity>;

type CommunityFormDefaults = Pick<NewCommunity, 'id' | 'creationDate' | 'isActive' | 'cinterests' | 'cactivities' | 'ccelebs'>;

type CommunityFormGroupContent = {
  id: FormControl<CommunityFormRawValue['id'] | NewCommunity['id']>;
  creationDate: FormControl<CommunityFormRawValue['creationDate']>;
  communityName: FormControl<CommunityFormRawValue['communityName']>;
  communityDescription: FormControl<CommunityFormRawValue['communityDescription']>;
  image: FormControl<CommunityFormRawValue['image']>;
  imageContentType: FormControl<CommunityFormRawValue['imageContentType']>;
  isActive: FormControl<CommunityFormRawValue['isActive']>;
  appuser: FormControl<CommunityFormRawValue['appuser']>;
  cinterests: FormControl<CommunityFormRawValue['cinterests']>;
  cactivities: FormControl<CommunityFormRawValue['cactivities']>;
  ccelebs: FormControl<CommunityFormRawValue['ccelebs']>;
};

export type CommunityFormGroup = FormGroup<CommunityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommunityFormService {
  createCommunityFormGroup(community?: CommunityFormGroupInput): CommunityFormGroup {
    const communityRawValue = this.convertCommunityToCommunityRawValue({
      ...this.getFormDefaults(),
      ...(community ?? { id: null }),
    });
    return new FormGroup<CommunityFormGroupContent>({
      id: new FormControl(
        { value: communityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      creationDate: new FormControl(communityRawValue.creationDate, {
        validators: [Validators.required],
      }),
      communityName: new FormControl(communityRawValue.communityName, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(100)],
      }),
      communityDescription: new FormControl(communityRawValue.communityDescription, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(7500)],
      }),
      image: new FormControl(communityRawValue.image),
      imageContentType: new FormControl(communityRawValue.imageContentType),
      isActive: new FormControl(communityRawValue.isActive),
      appuser: new FormControl(communityRawValue.appuser, {
        validators: [Validators.required],
      }),
      cinterests: new FormControl(communityRawValue.cinterests ?? []),
      cactivities: new FormControl(communityRawValue.cactivities ?? []),
      ccelebs: new FormControl(communityRawValue.ccelebs ?? []),
    });
  }

  getCommunity(form: CommunityFormGroup): ICommunity | NewCommunity {
    return this.convertCommunityRawValueToCommunity(form.getRawValue() as CommunityFormRawValue | NewCommunityFormRawValue);
  }

  resetForm(form: CommunityFormGroup, community: CommunityFormGroupInput): void {
    const communityRawValue = this.convertCommunityToCommunityRawValue({ ...this.getFormDefaults(), ...community });
    form.reset({
      ...communityRawValue,
      id: { value: communityRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): CommunityFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
      isActive: false,
      cinterests: [],
      cactivities: [],
      ccelebs: [],
    };
  }

  private convertCommunityRawValueToCommunity(rawCommunity: CommunityFormRawValue | NewCommunityFormRawValue): ICommunity | NewCommunity {
    return {
      ...rawCommunity,
      creationDate: dayjs(rawCommunity.creationDate, DATE_TIME_FORMAT),
    };
  }

  private convertCommunityToCommunityRawValue(
    community: ICommunity | (Partial<NewCommunity> & CommunityFormDefaults),
  ): CommunityFormRawValue | PartialWithRequiredKeyOf<NewCommunityFormRawValue> {
    return {
      ...community,
      creationDate: community.creationDate ? community.creationDate.format(DATE_TIME_FORMAT) : undefined,
      cinterests: community.cinterests ?? [],
      cactivities: community.cactivities ?? [],
      ccelebs: community.ccelebs ?? [],
    };
  }
}
