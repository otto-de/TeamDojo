import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IBadgeSkill } from 'app/shared/model/badge-skill.model';
import { BadgeSkillService } from './badge-skill.service';
import { IBadge } from 'app/shared/model/badge.model';
import { BadgeService } from 'app/entities/badge';

@Component({
    selector: 'jhi-badge-skill-update',
    templateUrl: './badge-skill-update.component.html'
})
export class BadgeSkillUpdateComponent implements OnInit {
    private _badgeSkill: IBadgeSkill;
    isSaving: boolean;

    badges: IBadge[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private badgeSkillService: BadgeSkillService,
        private badgeService: BadgeService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ badgeSkill }) => {
            this.badgeSkill = badgeSkill.body ? badgeSkill.body : badgeSkill;
        });
        this.badgeService.query().subscribe(
            (res: HttpResponse<IBadge[]>) => {
                this.badges = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.badgeSkill.id !== undefined) {
            this.subscribeToSaveResponse(this.badgeSkillService.update(this.badgeSkill));
        } else {
            this.subscribeToSaveResponse(this.badgeSkillService.create(this.badgeSkill));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBadgeSkill>>) {
        result.subscribe((res: HttpResponse<IBadgeSkill>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: IBadgeSkill) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackBadgeById(index: number, item: IBadge) {
        return item.id;
    }
    get badgeSkill() {
        return this._badgeSkill;
    }

    set badgeSkill(badgeSkill: IBadgeSkill) {
        this._badgeSkill = badgeSkill;
    }
}
