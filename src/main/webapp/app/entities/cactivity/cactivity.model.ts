import { ICommunity } from 'app/entities/community/community.model';

export interface ICactivity {
  id: number;
  activityName?: string | null;
  communities?: Pick<ICommunity, 'id'>[] | null;
}

export type NewCactivity = Omit<ICactivity, 'id'> & { id: null };
