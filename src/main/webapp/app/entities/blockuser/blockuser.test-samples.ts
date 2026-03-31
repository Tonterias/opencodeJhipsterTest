import dayjs from 'dayjs/esm';

import { IBlockuser, NewBlockuser } from './blockuser.model';

export const sampleWithRequiredData: IBlockuser = {
  id: 23388,
};

export const sampleWithPartialData: IBlockuser = {
  id: 20824,
  creationDate: dayjs('2026-03-30T17:13'),
};

export const sampleWithFullData: IBlockuser = {
  id: 10189,
  creationDate: dayjs('2026-03-30T16:33'),
};

export const sampleWithNewData: NewBlockuser = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
