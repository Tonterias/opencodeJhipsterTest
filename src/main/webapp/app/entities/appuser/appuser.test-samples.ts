import dayjs from 'dayjs/esm';

import { IAppuser, NewAppuser } from './appuser.model';

export const sampleWithRequiredData: IAppuser = {
  id: 19407,
  creationDate: dayjs('2026-03-30T23:52'),
};

export const sampleWithPartialData: IAppuser = {
  id: 11106,
  creationDate: dayjs('2026-03-31T06:57'),
  bio: 'bitterly up split',
  twitter: 'wearily',
  linkedin: 'but requirement',
};

export const sampleWithFullData: IAppuser = {
  id: 1902,
  creationDate: dayjs('2026-03-30T10:29'),
  bio: 'keenly wriggler insist',
  facebook: 'godfather whether remark',
  twitter: 'yuck accountability palate',
  linkedin: 'total boo despite',
  instagram: 'glossy',
  birthdate: dayjs('2026-03-30T22:24'),
};

export const sampleWithNewData: NewAppuser = {
  creationDate: dayjs('2026-03-31T01:54'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
