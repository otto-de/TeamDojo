import { ILevelSkill } from './level-skill.model';

export interface ILevel {
    id?: number;
    name?: string;
    description?: string;
    pictureContentType?: string;
    picture?: any;
    requiredScore?: number;
    dimensionName?: string;
    dimensionId?: number;
    dependsOnName?: string;
    dependsOnId?: number;
    skills?: ILevelSkill[];
}

export class Level implements ILevel {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public pictureContentType?: string,
        public picture?: any,
        public requiredScore?: number,
        public dimensionName?: string,
        public dimensionId?: number,
        public dependsOnName?: string,
        public dependsOnId?: number,
        public skills?: ILevelSkill[]
    ) {}
}
