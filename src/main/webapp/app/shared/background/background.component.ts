import { Component } from '@angular/core';
import { TeamsSelectionService } from '../../teams/teams-selection/teams-selection.service';
import { ITeam } from '../model/team.model';

@Component({
    selector: 'jhi-background-component',
    templateUrl: './background.component.html',
    styleUrls: ['./background.scss']
})
export class BackgroundComponent {
    constructor(private teamsSelectionService: TeamsSelectionService) {}

    get currentTeam(): ITeam {
        return this.teamsSelectionService.selectedTeam;
    }
}
