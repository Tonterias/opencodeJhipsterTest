import { IConfigVariables, NewConfigVariables } from './config-variables.model';

export const sampleWithRequiredData: IConfigVariables = {
  id: 22518,
};

export const sampleWithPartialData: IConfigVariables = {
  id: 5502,
  configVarLong1: 2499,
  configVarLong2: 17356,
  configVarLong3: 7053,
  configVarLong4: 13768,
  configVarLong5: 5983,
  configVarLong6: 12318,
  configVarLong7: 18997,
  configVarLong12: 20024,
  configVarLong13: 19339,
  configVarLong14: 28528,
  configVarLong15: 18832,
  configVarBoolean17: true,
  configVarString19: 'dearly reprimand',
  configVarString20: 'even kindheartedly',
};

export const sampleWithFullData: IConfigVariables = {
  id: 25730,
  configVarLong1: 32679,
  configVarLong2: 4406,
  configVarLong3: 23682,
  configVarLong4: 3590,
  configVarLong5: 554,
  configVarLong6: 28664,
  configVarLong7: 20754,
  configVarLong8: 13159,
  configVarLong9: 1972,
  configVarLong10: 25064,
  configVarLong11: 16225,
  configVarLong12: 24903,
  configVarLong13: 26259,
  configVarLong14: 10671,
  configVarLong15: 32763,
  configVarBoolean16: true,
  configVarBoolean17: true,
  configVarBoolean18: true,
  configVarString19: 'decryption righteously apud',
  configVarString20: 'the decouple comfortable',
};

export const sampleWithNewData: NewConfigVariables = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
