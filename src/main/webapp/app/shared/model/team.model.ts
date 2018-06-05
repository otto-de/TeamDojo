import { IDimension } from './dimension.model';
import { ITeamSkill } from './team-skill.model';

export interface ITeam {
    id?: number;
    name?: string;
    shortName?: string;
    pictureContentType?: string;
    picture?: any;
    slogan?: string;
    contactPerson?: string;
    participations?: IDimension[];
    skills?: ITeamSkill[];
    imageId?: number;
}

export class Team implements ITeam {
    constructor(
        public id?: number,
        public name?: string,
        public shortName?: string,
        public pictureContentType?: string,
        public picture?: any,
        public slogan?: string,
        public contactPerson?: string,
        public participations?: IDimension[],
        public skills?: ITeamSkill[],
        public imageId?: number
    ) {}
}
