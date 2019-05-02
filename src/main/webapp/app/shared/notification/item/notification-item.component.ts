import { Component, Input, OnChanges } from '@angular/core';
import { INotification } from 'app/shared/notification/model/notification.model';
import { IBadge } from 'app/shared/model/badge.model';
import { ITeam } from 'app/shared/model/team.model';

@Component({
    selector: 'jhi-notification-item',
    templateUrl: './notification-item.component.html',
    styleUrls: ['./notification-item.scss']
})
export class NotificationItemComponent implements OnChanges {
    @Input() notification: INotification;
    @Input() teams: ITeam[];
    @Input() badges: IBadge[];
    picture: string;
    item: IBadge | ITeam;

    constructor() {}

    ngOnChanges(): void {
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

    private sanitize(value: any): any {
        // ngxTranslate has a bug that prevents some special charaters to be
        // inside the string that you want to substitute.
        // This is why we are substituting these characters with same looking
        // UTF-8 characters.
        // Link: https://github.com/ngx-translate/core/issues/569#
        if (value !== null && typeof value !== 'undefined' && value !== 'undefined') {
            return value
                .replace(/\"/g, '″')
                .replace(/:/g, '᛬')
                .replace(/'/g, '′');
        }
        return null;
    }
}
