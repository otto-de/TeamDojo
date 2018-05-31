import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
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
            const badgeId = this.notification.data.badgeId;
            this.item = this.badges.find((b: IBadge) => b.id === badgeId);
        } else if (type.toString() === 'SKILL_COMPLETED') {
            const teamId = this.notification.data.teamId;
            this.item = this.teams.find((t: ITeam) => t.id === teamId);
        }
    }
}
