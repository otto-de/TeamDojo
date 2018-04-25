import { Moment } from 'moment';

export interface IAchievableSkill {
    teamSkillId?: number;
    skillId?: number;
    title?: string;
    achievedAt?: Moment;
}

export class AchievableSkill implements IAchievableSkill {
    constructor(public teamSkillId?: number, public skillId?: number, public title?: string, public achievedAt?: Moment) {}
}
