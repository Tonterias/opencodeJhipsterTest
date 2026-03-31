import dayjs from 'dayjs/esm';

import { IComment, NewComment } from './comment.model';

export const sampleWithRequiredData: IComment = {
  id: 20452,
  creationDate: dayjs('2026-03-30T23:39'),
  commentText: 'up knottily',
};

export const sampleWithPartialData: IComment = {
  id: 6771,
  creationDate: dayjs('2026-03-30T21:44'),
  commentText: 'yum',
  isOffensive: true,
};

export const sampleWithFullData: IComment = {
  id: 28427,
  creationDate: dayjs('2026-03-30T21:25'),
  commentText: 'trolley',
  isOffensive: true,
};

export const sampleWithNewData: NewComment = {
  creationDate: dayjs('2026-03-31T06:25'),
  commentText: 'hence sequester',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
