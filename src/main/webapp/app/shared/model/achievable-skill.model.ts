import { Moment } from 'moment';

export interface IAchievableSkill {
    teamSkillId?: number;
    skillId?: number;
    title?: string;
    description?: string;
    achievedAt?: Moment;
    verifiedAt?: Moment;
    vote?: number;
    voters?: string;
    irrelevant?: boolean;
    rateScore?: number;
    rateCount?: number;
}

export class AchievableSkill implements IAchievableSkill {
    constructor(
        public teamSkillId?: number,
        public skillId?: number,
        public title?: string,
        public description?: string,
        public achievedAt?: Moment,
        public verifiedAt?: Moment,
        public vote?: number,
        public voters?: string,
        public irrelevant?: boolean,
        public rateScore?: number,
        public rateCount?: number
    ) {}
}
