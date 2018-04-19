import { ITeamSkill } from './team-skill.model';

export interface ISkill {
    id?: number;
    title?: string;
    description?: string;
    validation?: string;
    expiryPeriod?: string;
    teams?: ITeamSkill[];
}

export class Skill implements ISkill {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public validation?: string,
        public expiryPeriod?: string,
        public teams?: ITeamSkill[]
    ) {}
}
