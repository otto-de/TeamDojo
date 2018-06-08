import { Component, Input } from '@angular/core';
import { TeamsSelectionService } from 'app/shared/teams-selection/teams-selection.service';
import { ITeam } from '../model/team.model';
import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { ISkill } from 'app/shared/model/skill.model';
import { IBadge } from 'app/shared/model/badge.model';

@Component({
    selector: 'jhi-background-component',
    templateUrl: './background.component.html',
    styleUrls: ['./background.scss']
})
export class BackgroundComponent {
    @Input() team: ITeam;
    @Input() teamSkills: ITeamSkill[] = [];
    @Input() skills: ISkill[] = [];
    @Input() badges: IBadge[] = [];
    constructor(private teamsSelectionService: TeamsSelectionService) {}

    get currentTeam(): ITeam {
        return this.team || this.teamsSelectionService.selectedTeam;
    }
}
