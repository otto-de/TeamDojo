export interface IBadgeSkill {
    id?: number;
    score?: number;
}

export class BadgeSkill implements IBadgeSkill {
    constructor(public id?: number, public score?: number) {}
}
