export interface IBadgeSkill {
    id?: number;
    badgeName?: string;
    badgeId?: number;
    skillTitle?: string;
    skillId?: number;
}

export class BadgeSkill implements IBadgeSkill {
    constructor(
        public id?: number,
        public badgeName?: string,
        public badgeId?: number,
        public skillTitle?: string,
        public skillId?: number
    ) {}
}
