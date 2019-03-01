import { Moment } from 'moment';
import { SkillStatus } from 'app/shared/model/skill-status';

export interface ITeamSkill {
    id?: number;
    completedAt?: Moment;
    verifiedAt?: Moment;
    irrelevant?: boolean;
    skillStatus?: SkillStatus;
    note?: string;
    skillTitle?: string;
    skillId?: number;
    teamName?: string;
    teamId?: number;
}

export class TeamSkill implements ITeamSkill {
    constructor(
        public id?: number,
        public completedAt?: Moment,
        public verifiedAt?: Moment,
        public irrelevant?: boolean,
        public skillStatus?: SkillStatus,
        public note?: string,
        public skillTitle?: string,
        public skillId?: number,
        public teamName?: string,
        public teamId?: number
    ) {
        this.irrelevant = false;
    }
}
