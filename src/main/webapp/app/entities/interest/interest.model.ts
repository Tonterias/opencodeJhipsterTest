import { IAppuser } from 'app/entities/appuser/appuser.model';

export interface IInterest {
  id: number;
  interestName?: string | null;
  appusers?: Pick<IAppuser, 'id'>[] | null;
}

export type NewInterest = Omit<IInterest, 'id'> & { id: null };
