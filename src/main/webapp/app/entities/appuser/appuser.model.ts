import dayjs from 'dayjs/esm';

import { IActivity } from 'app/entities/activity/activity.model';
import { ICeleb } from 'app/entities/celeb/celeb.model';
import { IInterest } from 'app/entities/interest/interest.model';
import { IUser } from 'app/entities/user/user.model';

export interface IAppuser {
  id: number;
  creationDate?: dayjs.Dayjs | null;
  bio?: string | null;
  facebook?: string | null;
  twitter?: string | null;
  linkedin?: string | null;
  instagram?: string | null;
  birthdate?: dayjs.Dayjs | null;
  user?: Pick<IUser, 'id'> | null;
  interests?: Pick<IInterest, 'id' | 'interestName'>[] | null;
  activities?: Pick<IActivity, 'id' | 'activityName'>[] | null;
  celebs?: Pick<ICeleb, 'id' | 'celebName'>[] | null;
}

export type NewAppuser = Omit<IAppuser, 'id'> & { id: null };
