import { Moment } from 'moment';

export interface ITeamSkill {
  id?: number;
  completedAt?: Moment;
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
    public completedAt?: Moment,
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
