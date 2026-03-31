import dayjs from 'dayjs/esm';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { ICommunity } from 'app/entities/community/community.model';

export interface IBlockuser {
  id: number;
  creationDate?: dayjs.Dayjs | null;
  blockeduser?: Pick<IAppuser, 'id'> | null;
  blockinguser?: Pick<IAppuser, 'id'> | null;
  cblockeduser?: Pick<ICommunity, 'id'> | null;
  cblockinguser?: Pick<ICommunity, 'id'> | null;
}

export type NewBlockuser = Omit<IBlockuser, 'id'> & { id: null };
