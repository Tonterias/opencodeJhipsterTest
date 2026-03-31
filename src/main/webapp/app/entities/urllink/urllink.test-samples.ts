import { IUrllink, NewUrllink } from './urllink.model';

export const sampleWithRequiredData: IUrllink = {
  id: 18078,
  linkText: 'scram',
  linkURL: 'between flat',
};

export const sampleWithPartialData: IUrllink = {
  id: 4400,
  linkText: 'substantial',
  linkURL: 'supposing ha nifty',
};

export const sampleWithFullData: IUrllink = {
  id: 26116,
  linkText: 'forenenst ha',
  linkURL: 'contrast reboot',
};

export const sampleWithNewData: NewUrllink = {
  linkText: 'even axe',
  linkURL: 'desk',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
