import dayjs from 'dayjs/esm';

import { IAppuser } from 'app/entities/appuser/appuser.model';

export interface IAppphoto {
  id: number;
  creationDate?: dayjs.Dayjs | null;
  image?: string | null;
  imageContentType?: string | null;
  appuser?: Pick<IAppuser, 'id'> | null;
}

export type NewAppphoto = Omit<IAppphoto, 'id'> & { id: null };
