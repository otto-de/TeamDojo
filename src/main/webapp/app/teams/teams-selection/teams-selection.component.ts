import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TeamsSelectionService } from 'app/teams/teams-selection/teams-selection.service';
import { TeamsService } from 'app/teams/teams.service';
import { Team } from 'app/shared/model/team.model';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-teams-selection',
    templateUrl: './teams-selection.component.html',
    styleUrls: ['./teams-selection.scss']
})
export class TeamsSelectionComponent implements OnInit {
    highlightedTeam: Team = null;

    teams: Team[] = [];

    constructor(
        private activeModal: NgbActiveModal,
        private teamsSelectionService: TeamsSelectionService,
        private teamsService: TeamsService,
        private router: Router
    ) {}

    ngOnInit(): void {
        this.teamsService.query().subscribe(teams => {
            this.teams = teams.body.sort((a, b) => a.shortName.localeCompare(b.shortName));
        });
        this.highlightedTeam = this.teamsSelectionService.selectedTeam;
    }

    selectTeam(team: Team) {
        this.highlightedTeam = team;
    }

    confirmTeam() {
        this.teamsSelectionService.selectedTeam = this.highlightedTeam;
        this.activeModal.close('Team selected');
        this.router.navigate(['teams', this.highlightedTeam.shortName]);
    }

    deselectTeam() {
        this.highlightedTeam = null;
        this.teamsSelectionService.selectedTeam = null;
        this.activeModal.close('No team selected');
    }

    cancelTeamSelection() {
        this.activeModal.dismiss('Team selected cancelled');
    }
}
