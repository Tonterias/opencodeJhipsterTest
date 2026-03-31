import { IPost } from 'app/entities/post/post.model';

export interface ITopic {
  id: number;
  topicName?: string | null;
  posts?: Pick<IPost, 'id' | 'headline'>[] | null;
}

export type NewTopic = Omit<ITopic, 'id'> & { id: null };
