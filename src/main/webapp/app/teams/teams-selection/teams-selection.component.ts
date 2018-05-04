import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TeamsSelectionService } from 'app/teams/teams-selection/teams-selection.service';
import { TeamsService } from 'app/teams/teams.service';
import { Team } from 'app/shared/model/team.model';

@Component({
    selector: 'jhi-teams-selection',
    templateUrl: './teams-selection.component.html',
    styleUrls: ['./teams-selection.scss']
})
export class TeamsSelectionComponent implements OnInit {
    private highlightedTeam: Team = null;

    private teams: Team[] = [];

    constructor(
        private activeModal: NgbActiveModal,
        private teamsSelectionService: TeamsSelectionService,
        private teamsService: TeamsService
    ) {}

    ngOnInit(): void {
        this.teamsService.query().subscribe(teams => {
            this.teams = teams.body;
        });
        this.highlightedTeam = this.teamsSelectionService.selectedTeam;
    }

    selectTeam(team: Team) {
        this.highlightedTeam = team;
    }

    confirmTeam() {
        this.teamsSelectionService.selectedTeam = this.highlightedTeam;
        this.activeModal.close('Team selected');
    }

    deselectTeam() {
        this.highlightedTeam = null;
        this.teamsSelectionService.selectedTeam = null;
        this.activeModal.close('No team selected');
    }

    cancelTeamSelection() {
        this.activeModal.dismiss('Team selected cancelled');
    }

    getTeamImage(team: Team) {
        return team.picture && team.pictureContentType ? `data:${team.pictureContentType};base64,${team.picture}` : null;
    }
}
