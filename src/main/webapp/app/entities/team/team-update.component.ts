import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiDataUtils } from 'ng-jhipster';

import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from './team.service';

@Component({
    selector: 'jhi-team-update',
    templateUrl: './team-update.component.html'
})
export class TeamUpdateComponent implements OnInit {
    private _team: ITeam;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private teamService: TeamService,
        private elementRef: ElementRef,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.team, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.team.id !== undefined) {
            this.subscribeToSaveResponse(this.teamService.update(this.team));
        } else {
            this.subscribeToSaveResponse(this.teamService.create(this.team));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>) {
        result.subscribe((res: HttpResponse<ITeam>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ITeam) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get team() {
        return this._team;
    }

    set team(team: ITeam) {
        this._team = team;
    }
}
