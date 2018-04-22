import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeamSkill } from 'app/shared/model/team-skill.model';

@Component({
    selector: 'jhi-team-skill-detail',
    templateUrl: './team-skill-detail.component.html'
})
export class TeamSkillDetailComponent implements OnInit {
    teamSkill: ITeamSkill;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ teamSkill }) => {
            this.teamSkill = teamSkill.body ? teamSkill.body : teamSkill;
        });
    }

    previousState() {
        window.history.back();
    }
}
