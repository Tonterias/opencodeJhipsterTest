import { IPost } from 'app/entities/post/post.model';

export interface ITag {
  id: number;
  tagName?: string | null;
  posts?: Pick<IPost, 'id' | 'headline'>[] | null;
}

export type NewTag = Omit<ITag, 'id'> & { id: null };
