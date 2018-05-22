import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { JhiDataUtils } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { ITeam } from 'app/shared/model/team.model';

@Component({
    selector: 'jhi-teams',
    templateUrl: './teams.component.html',
    styleUrls: ['./teams.scss']
})
export class TeamsComponent implements OnInit {
    @Output() changeTeam = new EventEmitter<any>();

    team: ITeam;

    constructor(private dataUtils: JhiDataUtils, private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ team }) => {
            this.team = team;
        });
        this.changeTeam.emit(this.team);
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
