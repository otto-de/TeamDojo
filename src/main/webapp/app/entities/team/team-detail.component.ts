import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeam } from 'app/shared/model/team.model';

@Component({
    selector: 'jhi-team-detail',
    templateUrl: './team-detail.component.html'
})
export class TeamDetailComponent implements OnInit {
    team: ITeam;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ team }) => {
            this.team = team.body ? team.body : team;
        });
    }

    previousState() {
        window.history.back();
    }
}
