import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActivity } from 'app/shared/model/activity.model';

@Component({
    selector: 'jhi-activity-detail',
    templateUrl: './activity-detail.component.html'
})
export class ActivityDetailComponent implements OnInit {
    activity: IActivity;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ activity }) => {
            this.activity = activity.body ? activity.body : activity;
        });
    }

    previousState() {
        window.history.back();
    }
}
