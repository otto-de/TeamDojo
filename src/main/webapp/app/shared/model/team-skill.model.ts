import { Moment } from 'moment';
import { ISkill } from './skill.model';
import { ITeam } from './team.model';

export interface ITeamSkill {
    id?: number;
    achievedAt?: Moment;
    verifiedAt?: Moment;
    note?: string;
    skill?: ISkill;
    team?: ITeam;
}

export class TeamSkill implements ITeamSkill {
    constructor(
        public id?: number,
        public achievedAt?: Moment,
        public verifiedAt?: Moment,
        public note?: string,
        public skill?: ISkill,
        public team?: ITeam
    ) {}
}
