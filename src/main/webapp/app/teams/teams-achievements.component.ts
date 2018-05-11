import { Component, OnInit, Input } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IBadge } from 'app/shared/model/badge.model';
import { ITeam } from 'app/shared/model/team.model';
import { ILevel } from 'app/shared/model/level.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { JhiAlertService } from 'ng-jhipster';
import { TeamsAchievementsService } from './teams-achievements.service';
import { sortLevels } from 'app/shared';

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
            const dimensionIds: number[] = this.team.participations.map((dimension: IDimension) => dimension.id);
            this.teamsAchievementsService.queryLevels({ 'dimensionId.in': dimensionIds }).subscribe(
                (res: HttpResponse<ILevel[]>) => {
                    const levels: { [key: number]: ILevel[] } = {};
                    if (res.body && res.body.length) {
                        res.body.forEach(
                            (level: ILevel) =>
                                levels[level.dimensionId] ? levels[level.dimensionId].push(level) : (levels[level.dimensionId] = [level])
                        );
                    } else {
                        dimensionIds.forEach((dimensionId: number) => (levels[dimensionId] = []));
                    }
                    for (const dimensionId in levels) {
                        if (levels.hasOwnProperty(dimensionId)) {
                            this.levels[dimensionId] = sortLevels(levels[dimensionId]);
                        }
                    }
                },
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
