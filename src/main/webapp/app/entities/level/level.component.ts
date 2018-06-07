import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILevel } from 'app/shared/model/level.model';
import { Principal } from 'app/core';
import { LevelService } from './level.service';

@Component({
    selector: 'jhi-level',
    templateUrl: './level.component.html'
})
export class LevelComponent implements OnInit, OnDestroy {
    levels: ILevel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private levelService: LevelService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.levelService.query().subscribe(
            (res: HttpResponse<ILevel[]>) => {
                this.levels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLevels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILevel) {
        return item.id;
    }

    registerChangeInLevels() {
        this.eventSubscriber = this.eventManager.subscribe('levelListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
