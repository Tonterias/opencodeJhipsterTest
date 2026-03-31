import { IAppuser } from 'app/entities/appuser/appuser.model';

export interface IActivity {
  id: number;
  activityName?: string | null;
  appusers?: Pick<IAppuser, 'id'>[] | null;
}

export type NewActivity = Omit<IActivity, 'id'> & { id: null };
