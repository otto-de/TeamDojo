import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { TeamSkillService } from './team-skill.service';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';

@Component({
    selector: 'jhi-team-skill-update',
    templateUrl: './team-skill-update.component.html'
})
export class TeamSkillUpdateComponent implements OnInit {
    private _teamSkill: ITeamSkill;
    isSaving: boolean;

    skills: ISkill[];

    teams: ITeam[];
    completedAt: string;
    verifiedAt: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private teamSkillService: TeamSkillService,
        private skillService: SkillService,
        private teamService: TeamService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ teamSkill }) => {
            this.teamSkill = teamSkill.body ? teamSkill.body : teamSkill;
        });
        this.skillService.query().subscribe(
            (res: HttpResponse<ISkill[]>) => {
                this.skills = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.teamService.query().subscribe(
            (res: HttpResponse<ITeam[]>) => {
                this.teams = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.teamSkill.completedAt = moment(this.completedAt, DATE_TIME_FORMAT);
        this.teamSkill.verifiedAt = moment(this.verifiedAt, DATE_TIME_FORMAT);
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSkillById(index: number, item: ISkill) {
        return item.id;
    }

    trackTeamById(index: number, item: ITeam) {
        return item.id;
    }
    get teamSkill() {
        return this._teamSkill;
    }

    set teamSkill(teamSkill: ITeamSkill) {
        this._teamSkill = teamSkill;
        this.completedAt = moment(teamSkill.completedAt).format();
        this.verifiedAt = moment(teamSkill.verifiedAt).format();
    }
}
