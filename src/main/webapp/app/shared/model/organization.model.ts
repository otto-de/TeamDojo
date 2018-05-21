export interface IOrganization {
    id?: number;
    name?: string;
}

export class Organization implements IOrganization {
    constructor(public id?: number, public name?: string) {}
}
