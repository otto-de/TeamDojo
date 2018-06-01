import { Injectable } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';
import { TeamsService } from 'app/teams/teams.service';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs/Observable';

const TEAM_STORAGE_KEY = 'selectedTeamId';

@Injectable()
export class TeamsSelectionService {
    private _selectedTeam: ITeam = null;

    constructor(private teamsService: TeamsService, private storage: LocalStorageService) {
        this.query();
    }

    query() {
        return this.teamsService.query().subscribe(result => {
            const teams = result.body;
            const teamIdStr = this.storage.retrieve(TEAM_STORAGE_KEY);
            if (teamIdStr !== null && !isNaN(Number(teamIdStr))) {
                const teamId = Number(teamIdStr);
                const team = teams.find(t => t.id === Number(teamId));
                this._selectedTeam = team ? team : null;
            }
        });
    }

    get selectedTeam() {
        return this._selectedTeam;
    }

    set selectedTeam(team: ITeam) {
        this._selectedTeam = team;
        if (team !== null) {
            this.storage.store(TEAM_STORAGE_KEY, this._selectedTeam.id.toString());
        } else {
            this.storage.clear(TEAM_STORAGE_KEY);
        }
    }
}
