import { Injectable } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';
import { TeamsService } from 'app/teams/teams.service';
import { LocalStorageService } from 'ngx-webstorage';
import { TeamSkillService } from 'app/entities/team-skill';

const TEAM_STORAGE_KEY = 'selectedTeamId';

@Injectable()
export class TeamsSelectionService {
    private _selectedTeam: ITeam = null;

    constructor(private teamsService: TeamsService, private teamSkillService: TeamSkillService, private storage: LocalStorageService) {
        this.query();
    }

    query() {
        const teamIdStr = this.storage.retrieve(TEAM_STORAGE_KEY);
        if (teamIdStr !== null && !isNaN(Number(teamIdStr))) {
            return this.teamsService.query().subscribe(result => {
                this._selectedTeam = (result.body || []).find(t => t.id === Number(teamIdStr)) || null;
                this.teamSkillService.query().subscribe(teamSkillRes => {
                    this._selectedTeam.skills = (teamSkillRes.body || []).filter(
                        teamSkill => this._selectedTeam && teamSkill.teamId === this._selectedTeam.id
                    );
                });
            });
        }
        return Observable.empty;
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
