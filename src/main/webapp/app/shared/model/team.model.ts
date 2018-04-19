import { IDimension } from './dimension.model';

export interface ITeam {
    id?: number;
    name?: string;
    pictureContentType?: string;
    picture?: any;
    contactPerson?: string;
    participations?: IDimension[];
}

export class Team implements ITeam {
    constructor(
        public id?: number,
        public name?: string,
        public pictureContentType?: string,
        public picture?: any,
        public contactPerson?: string,
        public participations?: IDimension[]
    ) {}
}
