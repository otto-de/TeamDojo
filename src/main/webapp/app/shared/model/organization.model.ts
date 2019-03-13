export interface IOrganization {
  id?: number;
  name?: string;
  levelUpScore?: number;
}

export class Organization implements IOrganization {
  constructor(public id?: number, public name?: string, public levelUpScore?: number) {}
}
