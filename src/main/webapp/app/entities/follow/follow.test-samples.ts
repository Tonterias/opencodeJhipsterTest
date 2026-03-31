import dayjs from 'dayjs/esm';

import { IFollow, NewFollow } from './follow.model';

export const sampleWithRequiredData: IFollow = {
  id: 9030,
};

export const sampleWithPartialData: IFollow = {
  id: 26695,
  creationDate: dayjs('2026-03-31T04:33'),
};

export const sampleWithFullData: IFollow = {
  id: 6595,
  creationDate: dayjs('2026-03-31T06:53'),
};

export const sampleWithNewData: NewFollow = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
