import dayjs from 'dayjs/esm';

export interface IFeedback {
  id: number;
  creationDate?: dayjs.Dayjs | null;
  name?: string | null;
  email?: string | null;
  feedback?: string | null;
}

export type NewFeedback = Omit<IFeedback, 'id'> & { id: null };
