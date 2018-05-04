import { Moment } from 'moment';

export interface ITeamSkill {
    id?: number;
    achievedAt?: Moment;
    verifiedAt?: Moment;
    irrelevant?: boolean;
    note?: string;
    skillTitle?: string;
    skillId?: number;
    teamName?: string;
    teamId?: number;
}

export class TeamSkill implements ITeamSkill {
    constructor(
        public id?: number,
        public achievedAt?: Moment,
        public verifiedAt?: Moment,
        public irrelevant?: boolean,
        public note?: string,
        public skillTitle?: string,
        public skillId?: number,
        public teamName?: string,
        public teamId?: number
    ) {
        this.irrelevant = false;
    }
}
