import { Moment } from 'moment';

export interface ISkillRate {
    skillId?: number;
    rate?: number;
}

export class SkillRate implements ISkillRate {
    constructor(public skillId?: number, public rate?: number) {}
}
