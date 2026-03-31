import dayjs from 'dayjs/esm';

import { IPost, NewPost } from './post.model';

export const sampleWithRequiredData: IPost = {
  id: 8730,
  creationDate: dayjs('2026-03-31T02:45'),
  headline: 'wrongly woefully alongside',
  bodytext: 'whenever',
};

export const sampleWithPartialData: IPost = {
  id: 18078,
  creationDate: dayjs('2026-03-31T00:30'),
  headline: 'huzzah',
  bodytext: 'emotional ah uh-huh',
  quote: 'stage about',
  linkText: 'helplessly litter before',
  linkURL: 'upwardly unlike',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithFullData: IPost = {
  id: 9917,
  creationDate: dayjs('2026-03-31T07:37'),
  publicationDate: dayjs('2026-03-30T22:13'),
  headline: 'if',
  leadtext: 'worth submissive',
  bodytext: 'incidentally daily infinite',
  quote: 'gloat',
  conclusion: 'lock finally midst',
  linkText: 'sundae psst aha',
  linkURL: 'provided yesterday ferret',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithNewData: NewPost = {
  creationDate: dayjs('2026-03-30T15:55'),
  headline: 'hype brisk',
  bodytext: 'however',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
