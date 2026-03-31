import { ICeleb, NewCeleb } from './celeb.model';

export const sampleWithRequiredData: ICeleb = {
  id: 31213,
  celebName: 'colour ouch',
};

export const sampleWithPartialData: ICeleb = {
  id: 31501,
  celebName: 'heartache eek',
};

export const sampleWithFullData: ICeleb = {
  id: 27058,
  celebName: 'aw offend',
};

export const sampleWithNewData: NewCeleb = {
  celebName: 'roger boastfully',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
