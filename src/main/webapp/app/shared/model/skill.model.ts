import { ITeamSkill } from './team-skill.model';
import { IBadgeSkill } from './badge-skill.model';
import { ILevelSkill } from './level-skill.model';

export interface ISkill {
    id?: number;
    title?: string;
    description?: string;
    validation?: string;
    expiryPeriod?: string;
    teams?: ITeamSkill[];
    badges?: IBadgeSkill[];
    levels?: ILevelSkill[];
}

export class Skill implements ISkill {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public validation?: string,
        public expiryPeriod?: string,
        public teams?: ITeamSkill[],
        public badges?: IBadgeSkill[],
        public levels?: ILevelSkill[]
    ) {}
}
