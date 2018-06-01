import { ITeam } from 'app/shared/model/team.model';

export interface TeamScore {
    team?: ITeam;
    score?: number;
}

export class TeamScore implements TeamScore {
    constructor(public team?: ITeam, public score?: number) {}
}
