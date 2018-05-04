export interface ILevelSkill {
    id?: number;
    score?: number;
    skillTitle?: string;
    skillId?: number;
    levelName?: string;
    levelId?: number;
}

export class LevelSkill implements ILevelSkill {
    constructor(
        public id?: number,
        public score?: number,
        public skillTitle?: string,
        public skillId?: number,
        public levelName?: string,
        public levelId?: number
    ) {}
}
