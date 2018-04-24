import { Component, Input, OnInit } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';
import { TeamsSkillsService } from './teams-skills.service';
import { ISkill } from 'app/shared/model/skill.model';

@Component({
    selector: 'jhi-teams-skills',
    templateUrl: './teams-skills.component.html',
    styles: []
})
export class TeamsSkillsComponent implements OnInit {
    @Input() team: ITeam;
    skills: ISkill[];

    constructor(private teamsSkillsService: TeamsSkillsService) {}

    ngOnInit() {
        this.teamsSkillsService.query().subscribe(response => {
            this.skills = response.body;
        });
    }
}
