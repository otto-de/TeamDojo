export interface ISkill {
    id?: number;
    title?: string;
    description?: string;
    validation?: string;
    expiryPeriod?: string;
}

export class Skill implements ISkill {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public validation?: string,
        public expiryPeriod?: string
    ) {}
}
