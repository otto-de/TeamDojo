import { ISkill } from './skill.model';
import { ILevel } from './level.model';

export interface ILevelSkill {
    id?: number;
    score?: number;
    skill?: ISkill;
    level?: ILevel;
}

export class LevelSkill implements ILevelSkill {
    constructor(public id?: number, public score?: number, public skill?: ISkill, public level?: ILevel) {}
}
