import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { LevelSkillService } from './level-skill.service';

@Component({
    selector: 'jhi-level-skill-update',
    templateUrl: './level-skill-update.component.html'
})
export class LevelSkillUpdateComponent implements OnInit {
    private _levelSkill: ILevelSkill;
    isSaving: boolean;

    constructor(private levelSkillService: LevelSkillService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ levelSkill }) => {
            this.levelSkill = levelSkill.body ? levelSkill.body : levelSkill;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.levelSkill.id !== undefined) {
            this.subscribeToSaveResponse(this.levelSkillService.update(this.levelSkill));
        } else {
            this.subscribeToSaveResponse(this.levelSkillService.create(this.levelSkill));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILevelSkill>>) {
        result.subscribe((res: HttpResponse<ILevelSkill>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ILevelSkill) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get levelSkill() {
        return this._levelSkill;
    }

    set levelSkill(levelSkill: ILevelSkill) {
        this._levelSkill = levelSkill;
    }
}
