import { ICommunity } from 'app/entities/community/community.model';

export interface ICceleb {
  id: number;
  celebName?: string | null;
  communities?: Pick<ICommunity, 'id'>[] | null;
}

export type NewCceleb = Omit<ICceleb, 'id'> & { id: null };
