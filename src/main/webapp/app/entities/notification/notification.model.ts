import dayjs from 'dayjs/esm';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { NotificationReason } from 'app/entities/enumerations/notification-reason.model';

export interface INotification {
  id: number;
  creationDate?: dayjs.Dayjs | null;
  notificationDate?: dayjs.Dayjs | null;
  notificationReason?: keyof typeof NotificationReason | null;
  notificationText?: string | null;
  isDelivered?: boolean | null;
  appuser?: Pick<IAppuser, 'id'> | null;
}

export type NewNotification = Omit<INotification, 'id'> & { id: null };
