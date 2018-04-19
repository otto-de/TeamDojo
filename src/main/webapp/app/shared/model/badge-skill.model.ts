import { IBadge } from './badge.model';
import { ISkill } from './skill.model';

export interface IBadgeSkill {
    id?: number;
    score?: number;
    badge?: IBadge;
    skill?: ISkill;
}

export class BadgeSkill implements IBadgeSkill {
    constructor(public id?: number, public score?: number, public badge?: IBadge, public skill?: ISkill) {}
}
