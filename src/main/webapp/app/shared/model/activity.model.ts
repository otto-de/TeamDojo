import { Moment } from 'moment';

export const enum ActivityType {
    'SKILL_COMPLETED',
    'BADGE_CREATED'
}

export interface IActivity {
    id?: number;
    type?: ActivityType;
    data?: string;
    createdAt?: Moment;
}

export class Activity implements IActivity {
    constructor(public id?: number, public type?: ActivityType, public data?: string, public createdAt?: Moment) {}
}
