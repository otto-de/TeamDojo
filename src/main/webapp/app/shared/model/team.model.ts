import { IDimension } from './dimension.model';
import { ITeamSkill } from './team-skill.model';

export interface ITeam {
    id?: number;
    name?: string;
    pictureContentType?: string;
    picture?: any;
    contactPerson?: string;
    participations?: IDimension[];
    skills?: ITeamSkill[];
}

export class Team implements ITeam {
    constructor(
        public id?: number,
        public name?: string,
        public pictureContentType?: string,
        public picture?: any,
        public contactPerson?: string,
        public participations?: IDimension[],
        public skills?: ITeamSkill[]
    ) {}
}
