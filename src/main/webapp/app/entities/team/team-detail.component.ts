import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITeam } from 'app/shared/model/team.model';

@Component({
    selector: 'jhi-team-detail',
    templateUrl: './team-detail.component.html'
})
export class TeamDetailComponent implements OnInit {
    team: ITeam;

    constructor(private dataUtils: JhiDataUtils, private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ team }) => {
            this.team = team.body ? team.body : team;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
