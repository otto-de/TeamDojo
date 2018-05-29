import { Component, OnInit } from '@angular/core';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity';

@Component({
    selector: 'jhi-activity',
    templateUrl: './activity.component.html',
    styleUrls: ['./activity.scss']
})
export class ActivityComponent implements OnInit {
    private activities: IActivity[];

    constructor(private activityService: ActivityService) {}

    ngOnInit(): void {
        this.activities = [];
        this.loadActivities();
    }

    loadActivities() {
        this.activityService.query().subscribe(res => {
            this.activities = res.body;
        });
    }
}
