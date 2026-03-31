export interface IConfigVariables {
  id: number;
  configVarLong1?: number | null;
  configVarLong2?: number | null;
  configVarLong3?: number | null;
  configVarLong4?: number | null;
  configVarLong5?: number | null;
  configVarLong6?: number | null;
  configVarLong7?: number | null;
  configVarLong8?: number | null;
  configVarLong9?: number | null;
  configVarLong10?: number | null;
  configVarLong11?: number | null;
  configVarLong12?: number | null;
  configVarLong13?: number | null;
  configVarLong14?: number | null;
  configVarLong15?: number | null;
  configVarBoolean16?: boolean | null;
  configVarBoolean17?: boolean | null;
  configVarBoolean18?: boolean | null;
  configVarString19?: string | null;
  configVarString20?: string | null;
}

export type NewConfigVariables = Omit<IConfigVariables, 'id'> & { id: null };
