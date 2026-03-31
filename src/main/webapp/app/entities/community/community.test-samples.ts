import dayjs from 'dayjs/esm';

import { ICommunity, NewCommunity } from './community.model';

export const sampleWithRequiredData: ICommunity = {
  id: 19290,
  creationDate: dayjs('2026-03-30T17:56'),
  communityName: 'despite pace',
  communityDescription: 'ugh',
};

export const sampleWithPartialData: ICommunity = {
  id: 73,
  creationDate: dayjs('2026-03-30T14:13'),
  communityName: 'even though',
  communityDescription: 'because',
  isActive: false,
};

export const sampleWithFullData: ICommunity = {
  id: 18848,
  creationDate: dayjs('2026-03-30T16:08'),
  communityName: 'woot',
  communityDescription: 'mmm cycle faithfully',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  isActive: false,
};

export const sampleWithNewData: NewCommunity = {
  creationDate: dayjs('2026-03-30T07:57'),
  communityName: 'for',
  communityDescription: 'upside-down reflecting palate',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
