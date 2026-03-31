import dayjs from 'dayjs/esm';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { IPost } from 'app/entities/post/post.model';

export interface IComment {
  id: number;
  creationDate?: dayjs.Dayjs | null;
  commentText?: string | null;
  isOffensive?: boolean | null;
  appuser?: Pick<IAppuser, 'id'> | null;
  post?: Pick<IPost, 'id'> | null;
}

export type NewComment = Omit<IComment, 'id'> & { id: null };
