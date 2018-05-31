import { ITeamSkill } from './team-skill.model';
import { IBadgeSkill } from './badge-skill.model';
import { ILevelSkill } from './level-skill.model';
import { IComment } from 'app/shared/model/comment.model';

export interface ISkill {
    id?: number;
    title?: string;
    description?: string;
    implementation?: string;
    validation?: string;
    expiryPeriod?: string;
    contact?: string;
    score?: number;
    rateScore?: number;
    rateCount?: number;
    teams?: ITeamSkill[];
    badges?: IBadgeSkill[];
    levels?: ILevelSkill[];
    comments?: IComment[];
}

export class Skill implements ISkill {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public implementation?: string,
        public validation?: string,
        public expiryPeriod?: string,
        public contact?: string,
        public rateScore?: number,
        public rateCount?: number,
        public score?: number,
        public teams?: ITeamSkill[],
        public badges?: IBadgeSkill[],
        public levels?: ILevelSkill[],
        public comments?: IComment[]
    ) {}
}
