import dayjs from 'dayjs/esm';

import { IFeedback, NewFeedback } from './feedback.model';

export const sampleWithRequiredData: IFeedback = {
  id: 16297,
  creationDate: dayjs('2026-03-30T14:15'),
  name: 'essence er',
  email: 'Santiago_ArellanoMaestas80@gmail.com',
  feedback: 'hence amidst',
};

export const sampleWithPartialData: IFeedback = {
  id: 20185,
  creationDate: dayjs('2026-03-31T03:01'),
  name: 'without though eyebrow',
  email: 'Gabriela.VegaNunez@gmail.com',
  feedback: 'energetically unto whoa',
};

export const sampleWithFullData: IFeedback = {
  id: 18312,
  creationDate: dayjs('2026-03-31T00:27'),
  name: 'fiercely',
  email: 'Gerardo61@hotmail.com',
  feedback: 'owlishly obnoxiously',
};

export const sampleWithNewData: NewFeedback = {
  creationDate: dayjs('2026-03-30T14:19'),
  name: 'developmental duh glaring',
  email: 'Irene.SamaniegoLeon92@yahoo.com',
  feedback: 'rawhide tomography regularly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
