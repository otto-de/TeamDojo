import { ITeam } from './team.model';
import { ILevel } from './level.model';

export interface IDimension {
    id?: number;
    name?: string;
    description?: string;
    participants?: ITeam[];
    levels?: ILevel[];
}

export class Dimension implements IDimension {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public participants?: ITeam[],
        public levels?: ILevel[]
    ) {}
}
