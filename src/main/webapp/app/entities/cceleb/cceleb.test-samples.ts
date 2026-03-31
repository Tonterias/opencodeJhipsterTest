import { ICceleb, NewCceleb } from './cceleb.model';

export const sampleWithRequiredData: ICceleb = {
  id: 16140,
  celebName: 'capitalize navigate',
};

export const sampleWithPartialData: ICceleb = {
  id: 23871,
  celebName: 'wildly facilitate',
};

export const sampleWithFullData: ICceleb = {
  id: 13403,
  celebName: 'topsail warp',
};

export const sampleWithNewData: NewCceleb = {
  celebName: 'near',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
