import { Moment } from 'moment';

export interface ISkillRate {
    skillId?: number;
    rateScore?: number;
    rateCount?: number;
}

export class SkillRate implements ISkillRate {
    constructor(public skillId?: number, public rateScore?: number, public rateCount?: number) {}
}
