export interface IUrllink {
  id: number;
  linkText?: string | null;
  linkURL?: string | null;
}

export type NewUrllink = Omit<IUrllink, 'id'> & { id: null };
