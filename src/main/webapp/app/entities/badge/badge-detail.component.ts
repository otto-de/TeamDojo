import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBadge } from 'app/shared/model/badge.model';

@Component({
    selector: 'jhi-badge-detail',
    templateUrl: './badge-detail.component.html'
})
export class BadgeDetailComponent implements OnInit {
    badge: IBadge;

    constructor(private dataUtils: JhiDataUtils, private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ badge }) => {
            this.badge = badge.body ? badge.body : badge;
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
