import { ICinterest, NewCinterest } from './cinterest.model';

export const sampleWithRequiredData: ICinterest = {
  id: 5699,
  interestName: 'zowie eek behind',
};

export const sampleWithPartialData: ICinterest = {
  id: 25523,
  interestName: 'digitize light afford',
};

export const sampleWithFullData: ICinterest = {
  id: 16073,
  interestName: 'weep inside',
};

export const sampleWithNewData: NewCinterest = {
  interestName: 'pish more scarper',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
