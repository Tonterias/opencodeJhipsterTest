import { IAppuser } from 'app/entities/appuser/appuser.model';

export interface ICeleb {
  id: number;
  celebName?: string | null;
  appusers?: Pick<IAppuser, 'id'>[] | null;
}

export type NewCeleb = Omit<ICeleb, 'id'> & { id: null };
