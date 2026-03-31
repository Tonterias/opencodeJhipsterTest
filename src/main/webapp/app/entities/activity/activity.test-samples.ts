import { IActivity, NewActivity } from './activity.model';

export const sampleWithRequiredData: IActivity = {
  id: 20972,
  activityName: 'scrap',
};

export const sampleWithPartialData: IActivity = {
  id: 9423,
  activityName: 'terribly till',
};

export const sampleWithFullData: IActivity = {
  id: 16264,
  activityName: 'beneath upright',
};

export const sampleWithNewData: NewActivity = {
  activityName: 'separately poorly generously',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
