import { ICactivity, NewCactivity } from './cactivity.model';

export const sampleWithRequiredData: ICactivity = {
  id: 5576,
  activityName: 'ah via reassemble',
};

export const sampleWithPartialData: ICactivity = {
  id: 28443,
  activityName: 'voluntarily',
};

export const sampleWithFullData: ICactivity = {
  id: 12006,
  activityName: 'till',
};

export const sampleWithNewData: NewCactivity = {
  activityName: 'furthermore following lawful',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
