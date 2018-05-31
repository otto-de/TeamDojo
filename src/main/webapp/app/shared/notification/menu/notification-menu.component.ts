import { Component, OnInit } from '@angular/core';
import { INotification, Notification } from 'app/shared/notification/model/notification.model';
import { IBadge } from 'app/shared/model/badge.model';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';
import { BadgeService } from 'app/entities/badge';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity';
import { JhiParseLinks } from 'ng-jhipster';

@Component({
    selector: 'jhi-notification-menu',
    templateUrl: './notification-menu.component.html',
    styleUrls: ['./notification-menu.scss']
})
export class NotificationMenuComponent implements OnInit {
    notifications: INotification[];
    teams: ITeam[];
    badges: IBadge[];
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;

    constructor(
        private activityService: ActivityService,
        private teamService: TeamService,
        private badgeService: BadgeService,
        private parseLinks: JhiParseLinks
    ) {
        this.itemsPerPage = 8;
        this.page = 0;
        this.links = {
            last: 0
        };
    }

    ngOnInit(): void {
        this.notifications = [];
        this.teams = [];
        this.teamService.query().subscribe(response => {
            this.teams = response.body;
        });
        this.badges = [];
        this.badgeService.query().subscribe(response => {
            this.badges = response.body;
        });
    }

    public loadNotifications(): void {
        this.page = 0;
        this.notifications = [];
        this.getNext();
    }

    getNext() {
        this.activityService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: ['createdAt,desc']
            })
            .subscribe(
                (res: HttpResponse<IActivity[]>) => this.paginateActivities(res.body, res.headers),
                (res: HttpErrorResponse) => console.log('Error getting Activities')
            );
    }

    loadPage(page) {
        this.page = page;
        this.getNext();
    }

    private paginateActivities(data: IActivity[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.notifications.push(new Notification(data[i], true));
        }
    }
}
