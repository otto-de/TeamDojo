import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { TeamSkillService } from './team-skill.service';

@Component({
    selector: 'jhi-team-skill-update',
    templateUrl: './team-skill-update.component.html'
})
export class TeamSkillUpdateComponent implements OnInit {
    private _teamSkill: ITeamSkill;
    isSaving: boolean;
    achievedAt: string;

    constructor(private teamSkillService: TeamSkillService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ teamSkill }) => {
            this.teamSkill = teamSkill.body ? teamSkill.body : teamSkill;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.teamSkill.achievedAt = moment(this.achievedAt, DATE_TIME_FORMAT);
        if (this.teamSkill.id !== undefined) {
            this.subscribeToSaveResponse(this.teamSkillService.update(this.teamSkill));
        } else {
            this.subscribeToSaveResponse(this.teamSkillService.create(this.teamSkill));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITeamSkill>>) {
        result.subscribe((res: HttpResponse<ITeamSkill>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ITeamSkill) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get teamSkill() {
        return this._teamSkill;
    }

    set teamSkill(teamSkill: ITeamSkill) {
        this._teamSkill = teamSkill;
        this.achievedAt = moment(teamSkill.achievedAt).format();
    }
}
