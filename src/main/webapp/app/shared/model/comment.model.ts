import { Moment } from 'moment';

export interface IComment {
    id?: number;
    text?: string;
    creationDate?: Moment;
    teamShortName?: string;
    teamId?: number;
    skillTitle?: string;
    skillId?: number;
}

export class Comment implements IComment {
    constructor(
        public id?: number,
        public text?: string,
        public creationDate?: Moment,
        public teamShortName?: string,
        public teamId?: number,
        public skillTitle?: string,
        public skillId?: number
    ) {}
}
