import { Injectable } from '@angular/core';
import { Team } from 'app/shared/model/team.model';
import { TeamsService } from 'app/teams/teams.service';

const TEAM_STORAGE_KEY = 'selectedTeamId';

@Injectable()
export class TeamsSelectionService {
    private _selectedTeam: Team = null;

    constructor(private teamsService: TeamsService) {
        // fetch all teams and filter out the selected team
        this.teamsService.query().subscribe(result => {
            const teams = result.body;
            const teamIdStr = localStorage.getItem(TEAM_STORAGE_KEY);
            if (teamIdStr !== null && !isNaN(Number(teamIdStr))) {
                const teamId = Number(teamIdStr);
                const team = teams.find(t => t.id === Number(teamId));
                this._selectedTeam = team ? team : null;
            }
        });
    }

    set selectedTeam(team: Team) {
        this._selectedTeam = team;
        if (team !== null) {
            localStorage.setItem(TEAM_STORAGE_KEY, this._selectedTeam.id.toString());
        } else {
            localStorage.removeItem(TEAM_STORAGE_KEY);
        }
    }

    get selectedTeam() {
        return this._selectedTeam;
    }
}
