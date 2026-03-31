import dayjs from 'dayjs/esm';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { ICactivity } from 'app/entities/cactivity/cactivity.model';
import { ICceleb } from 'app/entities/cceleb/cceleb.model';
import { ICinterest } from 'app/entities/cinterest/cinterest.model';

export interface ICommunity {
  id: number;
  creationDate?: dayjs.Dayjs | null;
  communityName?: string | null;
  communityDescription?: string | null;
  image?: string | null;
  imageContentType?: string | null;
  isActive?: boolean | null;
  appuser?: Pick<IAppuser, 'id'> | null;
  cinterests?: Pick<ICinterest, 'id' | 'interestName'>[] | null;
  cactivities?: Pick<ICactivity, 'id' | 'activityName'>[] | null;
  ccelebs?: Pick<ICceleb, 'id' | 'celebName'>[] | null;
}

export type NewCommunity = Omit<ICommunity, 'id'> & { id: null };
