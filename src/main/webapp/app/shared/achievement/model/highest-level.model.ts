import { IDimension } from 'app/shared/model/dimension.model';
import { ILevel } from 'app/shared/model/level.model';

export interface IHighestLevel {
    dimension?: IDimension;
    level?: ILevel;
    ordinal?: number;
}

export class HighestLevel implements IHighestLevel {
    constructor(public dimension?: IDimension, public level?: ILevel, public ordinal?: number) {}
}
