export interface IBadgeSkill {
    id?: number;
    score?: number;
    badgeName?: string;
    badgeId?: number;
    skillTitle?: string;
    skillId?: number;
}

export class BadgeSkill implements IBadgeSkill {
    constructor(
        public id?: number,
        public score?: number,
        public badgeName?: string,
        public badgeId?: number,
        public skillTitle?: string,
        public skillId?: number
    ) {}
}
