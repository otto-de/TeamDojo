import { Moment } from 'moment';
import { ISkill } from './skill.model';
import { ITeam } from './team.model';

export interface ITeamSkill {
    id?: number;
    achievedAt?: Moment;
    verified?: boolean;
    note?: string;
    skill?: ISkill;
    team?: ITeam;
}

export class TeamSkill implements ITeamSkill {
    constructor(
        public id?: number,
        public achievedAt?: Moment,
        public verified?: boolean,
        public note?: string,
        public skill?: ISkill,
        public team?: ITeam
    ) {
        this.verified = false;
    }
}
