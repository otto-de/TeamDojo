export interface IDimension {
    id?: number;
    name?: string;
    description?: string;
}

export class Dimension implements IDimension {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
