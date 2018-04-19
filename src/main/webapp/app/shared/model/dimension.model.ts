import { ITeam } from './team.model';

export interface IDimension {
    id?: number;
    name?: string;
    description?: string;
    participants?: ITeam[];
}

export class Dimension implements IDimension {
    constructor(public id?: number, public name?: string, public description?: string, public participants?: ITeam[]) {}
}
