import dayjs from 'dayjs/esm';

import { IAppphoto, NewAppphoto } from './appphoto.model';

export const sampleWithRequiredData: IAppphoto = {
  id: 9256,
  creationDate: dayjs('2026-03-31T00:20'),
};

export const sampleWithPartialData: IAppphoto = {
  id: 3985,
  creationDate: dayjs('2026-03-30T17:31'),
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithFullData: IAppphoto = {
  id: 30920,
  creationDate: dayjs('2026-03-30T12:33'),
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithNewData: NewAppphoto = {
  creationDate: dayjs('2026-03-31T05:26'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
