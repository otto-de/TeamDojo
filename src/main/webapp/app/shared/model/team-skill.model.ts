import { Moment } from 'moment';
import { ISkill } from './skill.model';
import { ITeam } from './team.model';

export interface ITeamSkill {
    id?: number;
    completedAt?: Moment;
    verifiedAt?: Moment;
    irrelevant?: boolean;
    note?: string;
    skill?: ISkill;
    team?: ITeam;
}

export class TeamSkill implements ITeamSkill {
    constructor(
        public id?: number,
        public completedAt?: Moment,
        public verifiedAt?: Moment,
        public irrelevant?: boolean,
        public note?: string,
        public skill?: ISkill,
        public team?: ITeam
    ) {
        this.irrelevant = false;
    }
}
