import { Component, OnInit, Input } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IBadge } from 'app/shared/model/badge.model';
import { ITeam } from 'app/shared/model/team.model';
import { ILevel } from 'app/shared/model/level.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { JhiAlertService } from 'ng-jhipster';
import { TeamsAchievementsService } from './teams-achievements.service';

@Component({
    selector: 'jhi-teams-achievements',
    templateUrl: './teams-achievements.component.html',
    styleUrls: ['teams-achievements.scss']
})
export class TeamsAchievementsComponent implements OnInit {
    @Input() team: ITeam;
    badges: IBadge[];
    levels: { [key: number]: ILevel[] };

    constructor(private teamsAchievementsService: TeamsAchievementsService, private jhiAlertService: JhiAlertService) {
        this.badges = [];
        this.levels = {};
    }

    ngOnInit() {
        this.loadAll();
    }

    loadAll() {
        this.teamsAchievementsService
            .queryBadges()
            .subscribe(
                (res: HttpResponse<IBadge[]>) => res.body.forEach((badge: IBadge) => this.badges.push(badge)),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        if (this.team.participations) {
            this.teamsAchievementsService
                .queryLevels({ 'dimensionId.in': this.team.participations.map((dimension: IDimension) => dimension.id) })
                .subscribe(
                    (res: HttpResponse<ILevel[]>) =>
                        res.body.forEach(
                            (level: ILevel) =>
                                this.levels[level.dimensionId]
                                    ? this.levels[level.dimensionId].push(level)
                                    : (this.levels[level.dimensionId] = [level])
                        ),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    trackId(index: number, item) {
        return item.id;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
