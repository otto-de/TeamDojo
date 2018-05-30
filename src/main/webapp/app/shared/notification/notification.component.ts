import { Component, Input, OnInit } from '@angular/core';
import { INotification } from 'app/shared/notification/model/notification.model';
import { IBadge } from 'app/shared/model/badge.model';
import { ITeam } from 'app/shared/model/team.model';

@Component({
    selector: 'jhi-notification',
    templateUrl: './notification.component.html',
    styleUrls: ['./notification.scss']
})
export class NotificationComponent implements OnInit {
    @Input() notification: INotification;
    @Input() teams: ITeam[];
    @Input() badges: IBadge[];
    picture: string;
    item: IBadge | ITeam;

    constructor() {}

    ngOnInit(): void {
        this.item = {};
        const type = this.notification.activity.type;
        if (type.toString() === 'BADGE_CREATED') {
            this.item.name = this.notification.data.badgeName;
        } else if (type.toString() === 'SKILL_COMPLETED') {
            this.item.name = this.notification.data.teamName;
        }
    }

    markAsRead(event) {
        event.preventDefault();
        console.log('read');
    }
}
