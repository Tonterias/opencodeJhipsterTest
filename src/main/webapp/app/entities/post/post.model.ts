import dayjs from 'dayjs/esm';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { IBlog } from 'app/entities/blog/blog.model';
import { ITag } from 'app/entities/tag/tag.model';
import { ITopic } from 'app/entities/topic/topic.model';

export interface IPost {
  id: number;
  creationDate?: dayjs.Dayjs | null;
  publicationDate?: dayjs.Dayjs | null;
  headline?: string | null;
  leadtext?: string | null;
  bodytext?: string | null;
  quote?: string | null;
  conclusion?: string | null;
  linkText?: string | null;
  linkURL?: string | null;
  image?: string | null;
  imageContentType?: string | null;
  appuser?: Pick<IAppuser, 'id'> | null;
  blog?: Pick<IBlog, 'id' | 'title'> | null;
  tags?: Pick<ITag, 'id' | 'tagName'>[] | null;
  topics?: Pick<ITopic, 'id' | 'topicName'>[] | null;
}

export type NewPost = Omit<IPost, 'id'> & { id: null };
