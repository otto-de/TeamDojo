import { IDimension } from './dimension.model';
import { ILevel } from './level.model';

export interface ILevel {
    id?: number;
    name?: string;
    pictureContentType?: string;
    picture?: any;
    requiredScore?: number;
    dimension?: IDimension;
    dependsOn?: ILevel;
}

export class Level implements ILevel {
    constructor(
        public id?: number,
        public name?: string,
        public pictureContentType?: string,
        public picture?: any,
        public requiredScore?: number,
        public dimension?: IDimension,
        public dependsOn?: ILevel
    ) {}
}
