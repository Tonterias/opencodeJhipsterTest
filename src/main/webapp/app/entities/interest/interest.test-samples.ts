import { IInterest, NewInterest } from './interest.model';

export const sampleWithRequiredData: IInterest = {
  id: 3032,
  interestName: 'red whoa',
};

export const sampleWithPartialData: IInterest = {
  id: 22161,
  interestName: 'hence fervently',
};

export const sampleWithFullData: IInterest = {
  id: 11917,
  interestName: 'meh',
};

export const sampleWithNewData: NewInterest = {
  interestName: 'aha fleck forgather',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
