export interface ILevelSkill {
    id?: number;
    score?: number;
}

export class LevelSkill implements ILevelSkill {
    constructor(public id?: number, public score?: number) {}
}
