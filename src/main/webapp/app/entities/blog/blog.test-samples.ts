import dayjs from 'dayjs/esm';

import { IBlog, NewBlog } from './blog.model';

export const sampleWithRequiredData: IBlog = {
  id: 8379,
  creationDate: dayjs('2026-03-31T03:45'),
  title: 'lively cash',
};

export const sampleWithPartialData: IBlog = {
  id: 15714,
  creationDate: dayjs('2026-03-30T18:03'),
  title: 'premise overreact enthusiastically',
};

export const sampleWithFullData: IBlog = {
  id: 16900,
  creationDate: dayjs('2026-03-30T19:17'),
  title: 'brr wherever failing',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithNewData: NewBlog = {
  creationDate: dayjs('2026-03-30T22:39'),
  title: 'deduct always',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
