import { Moment } from 'moment';
import { IBadgeSkill } from './badge-skill.model';
import { IDimension } from './dimension.model';

export interface IBadge {
    id?: number;
    name?: string;
    description?: string;
    pictureContentType?: string;
    picture?: any;
    availableUntil?: Moment;
    availableAmount?: number;
    requiredScore?: number;
    instantMultiplier?: number;
    completionBonus?: number;
    skills?: IBadgeSkill[];
    dimensions?: IDimension[];
}

export class Badge implements IBadge {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public pictureContentType?: string,
        public picture?: any,
        public availableUntil?: Moment,
        public availableAmount?: number,
        public requiredScore?: number,
        public instantMultiplier?: number,
        public completionBonus?: number,
        public skills?: IBadgeSkill[],
        public dimensions?: IDimension[]
    ) {}
}
