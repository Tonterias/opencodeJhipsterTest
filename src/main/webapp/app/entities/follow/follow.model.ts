import dayjs from 'dayjs/esm';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { ICommunity } from 'app/entities/community/community.model';

export interface IFollow {
  id: number;
  creationDate?: dayjs.Dayjs | null;
  followed?: Pick<IAppuser, 'id'> | null;
  following?: Pick<IAppuser, 'id'> | null;
  cfollowed?: Pick<ICommunity, 'id'> | null;
  cfollowing?: Pick<ICommunity, 'id'> | null;
}

export type NewFollow = Omit<IFollow, 'id'> & { id: null };
