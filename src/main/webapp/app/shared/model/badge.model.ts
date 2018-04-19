import { Moment } from 'moment';
import { IBadgeSkill } from './badge-skill.model';

export interface IBadge {
    id?: number;
    name?: string;
    description?: string;
    logoContentType?: string;
    logo?: any;
    availableUntil?: Moment;
    availableAmount?: number;
    requiredScore?: number;
    skills?: IBadgeSkill[];
}

export class Badge implements IBadge {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public logoContentType?: string,
        public logo?: any,
        public availableUntil?: Moment,
        public availableAmount?: number,
        public requiredScore?: number,
        public skills?: IBadgeSkill[]
    ) {}
}
