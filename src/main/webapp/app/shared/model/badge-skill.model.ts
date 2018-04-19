import { IBadge } from './badge.model';

export interface IBadgeSkill {
    id?: number;
    score?: number;
    badge?: IBadge;
}

export class BadgeSkill implements IBadgeSkill {
    constructor(public id?: number, public score?: number, public badge?: IBadge) {}
}
