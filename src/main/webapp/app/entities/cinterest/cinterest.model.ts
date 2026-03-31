import { ICommunity } from 'app/entities/community/community.model';

export interface ICinterest {
  id: number;
  interestName?: string | null;
  communities?: Pick<ICommunity, 'id'>[] | null;
}

export type NewCinterest = Omit<ICinterest, 'id'> & { id: null };
