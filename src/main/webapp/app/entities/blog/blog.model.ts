import dayjs from 'dayjs/esm';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { ICommunity } from 'app/entities/community/community.model';

export interface IBlog {
  id: number;
  creationDate?: dayjs.Dayjs | null;
  title?: string | null;
  image?: string | null;
  imageContentType?: string | null;
  appuser?: Pick<IAppuser, 'id'> | null;
  community?: Pick<ICommunity, 'id' | 'communityName'> | null;
}

export type NewBlog = Omit<IBlog, 'id'> & { id: null };
