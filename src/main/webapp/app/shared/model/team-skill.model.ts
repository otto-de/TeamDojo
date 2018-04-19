import { Moment } from 'moment';

export interface ITeamSkill {
    id?: number;
    achievedAt?: Moment;
    verified?: boolean;
    note?: string;
}

export class TeamSkill implements ITeamSkill {
    constructor(public id?: number, public achievedAt?: Moment, public verified?: boolean, public note?: string) {
        this.verified = false;
    }
}
