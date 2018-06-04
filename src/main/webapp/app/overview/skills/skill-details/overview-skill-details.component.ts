import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ILevel } from 'app/shared/model/level.model';
import { TeamsSkillsService } from 'app/teams/teams-skills.service';
import { SkillDetailsComponentParent } from 'app/shared/skill-details/skill-details.component';

@Component({
    selector: 'jhi-overview-skill-details',
    templateUrl: './overview-skill-details.component.html',
    styleUrls: ['./overview-skill-details.scss']
})
export class OverviewSkillDetailsComponent extends SkillDetailsComponentParent implements OnInit {
    constructor(route: ActivatedRoute, teamsSkillsService: TeamsSkillsService) {
        super(route, teamsSkillsService);
    }

    ngOnInit(): void {
        this.route.data.subscribe((resolvedData: { team; teams; skill; comments; selectedTeam; teamSkills; badges; skills }) => {
            super.setResolvedData(resolvedData);
        });
        super.loadData();
    }
}
