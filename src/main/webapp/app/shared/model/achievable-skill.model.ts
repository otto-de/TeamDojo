import { Moment } from 'moment';
import { SkillStatus } from 'app/shared/model/skill-status';

export interface IAchievableSkill {
    teamSkillId?: number;
    skillId?: number;
    title?: string;
    description?: string;
    achievedAt?: Moment;
    irrelevant?: boolean;
    skillStatus?: SkillStatus;
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
        public irrelevant?: boolean,
        public skillStatus?: SkillStatus,
        public rateScore?: number,
        public rateCount?: number
    ) {}
}
