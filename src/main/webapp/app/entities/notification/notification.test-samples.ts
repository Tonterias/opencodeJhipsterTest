import dayjs from 'dayjs/esm';

import { INotification, NewNotification } from './notification.model';

export const sampleWithRequiredData: INotification = {
  id: 10110,
  creationDate: dayjs('2026-03-30T22:17'),
  notificationReason: 'FOLLOWING',
};

export const sampleWithPartialData: INotification = {
  id: 19694,
  creationDate: dayjs('2026-03-30T07:44'),
  notificationReason: 'FOLLOWING',
  isDelivered: false,
};

export const sampleWithFullData: INotification = {
  id: 5787,
  creationDate: dayjs('2026-03-30T18:38'),
  notificationDate: dayjs('2026-03-31T00:31'),
  notificationReason: 'UNAUTHORIZE_COMMUNITY_FOLLOWER',
  notificationText: 'stained violently cinema',
  isDelivered: false,
};

export const sampleWithNewData: NewNotification = {
  creationDate: dayjs('2026-03-31T03:06'),
  notificationReason: 'UNAUTHORIZE_COMMUNITY_FOLLOWER',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
