import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILevel } from 'app/shared/model/level.model';

@Component({
    selector: 'jhi-level-detail',
    templateUrl: './level-detail.component.html'
})
export class LevelDetailComponent implements OnInit {
    level: ILevel;

    constructor(private dataUtils: JhiDataUtils, private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ level }) => {
            this.level = level.body ? level.body : level;
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
