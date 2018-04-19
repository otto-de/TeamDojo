import { ITeamSkill } from './team-skill.model';
import { IBadgeSkill } from './badge-skill.model';

export interface ISkill {
    id?: number;
    title?: string;
    description?: string;
    validation?: string;
    expiryPeriod?: string;
    teams?: ITeamSkill[];
    badges?: IBadgeSkill[];
}

export class Skill implements ISkill {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public validation?: string,
        public expiryPeriod?: string,
        public teams?: ITeamSkill[],
        public badges?: IBadgeSkill[]
    ) {}
}
