export interface ITeam {
    id?: number;
    name?: string;
    pictureContentType?: string;
    picture?: any;
    contactPerson?: string;
}

export class Team implements ITeam {
    constructor(
        public id?: number,
        public name?: string,
        public pictureContentType?: string,
        public picture?: any,
        public contactPerson?: string
    ) {}
}
