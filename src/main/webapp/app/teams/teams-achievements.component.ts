import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IBadge } from 'app/shared/model/badge.model';
import { ITeam } from 'app/shared/model/team.model';
import { ILevel } from 'app/shared/model/level.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { JhiAlertService } from 'ng-jhipster';
import { TeamsAchievementsService } from './teams-achievements.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'jhi-teams-achievements',
    templateUrl: './teams-achievements.component.html',
    styleUrls: ['teams-achievements.scss']
})
export class TeamsAchievementsComponent implements OnInit, OnDestroy {
    @Input() team: ITeam;
    badges: IBadge[];
    levels: { [key: number]: ILevel[] };
    activeDimensionCssId: string;
    private defaultDimensionCssId: string;
    levelCssId: string;
    subscriptions: Subscription[];

    constructor(
        private route: ActivatedRoute,
        private teamsAchievementsService: TeamsAchievementsService,
        private jhiAlertService: JhiAlertService
    ) {
        this.badges = [];
        this.levels = {};
        this.defaultDimensionCssId = '';
        this.subscriptions = [];
    }

    ngOnInit() {
        if (this.team.participations && this.team.participations[0]) {
            this.defaultDimensionCssId = `achievements-dimension-${this.team.participations[0].id}`;
        }
        this.activeDimensionCssId = this.defaultDimensionCssId;
        this.subscriptions.push(
            this.route.paramMap.subscribe((params: ParamMap) => {
                const dimensionCssId = params.get('dimension');
                this.levelCssId = params.get('level');
                if (dimensionCssId) {
                    this.activeDimensionCssId = dimensionCssId;
                } else {
                    this.activeDimensionCssId = this.defaultDimensionCssId;
                }
                this.onDimensionChange(this.activeDimensionCssId);
            })
        );
        this.loadAll();
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subscription => subscription.unsubscribe());
    }

    onDimensionChange(panelId: string) {
        if (panelId === this.activeDimensionCssId && this.levelCssId && document.getElementById(this.levelCssId)) {
            document.getElementById(this.levelCssId).scrollIntoView({ block: 'end', behavior: 'smooth' });
        }
    }

    loadAll() {
        this.subscriptions.push(
            this.teamsAchievementsService
                .queryBadges()
                .subscribe(
                    (res: HttpResponse<IBadge[]>) => res.body.forEach((badge: IBadge) => this.badges.push(badge)),
                    (res: HttpErrorResponse) => this.onError(res.message)
                )
        );
        if (this.team.participations) {
            const dimensionIds: number[] = this.team.participations.map((dimension: IDimension) => dimension.id);
            this.subscriptions.push(
                this.teamsAchievementsService.queryLevels({ 'dimensionId.in': dimensionIds }).subscribe(
                    (res: HttpResponse<ILevel[]>) => {
                        const levels: { [key: number]: ILevel[] } = {};
                        if (res.body && res.body.length) {
                            res.body.forEach(
                                (level: ILevel) =>
                                    levels[level.dimensionId]
                                        ? levels[level.dimensionId].push(level)
                                        : (levels[level.dimensionId] = [level])
                            );
                        } else {
                            dimensionIds.forEach((dimensionId: number) => (levels[dimensionId] = []));
                        }
                        for (const dimensionId in levels) {
                            if (levels.hasOwnProperty(dimensionId)) {
                                this.levels[dimensionId] = this._sortLevels(levels[dimensionId]);
                            }
                        }
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                )
            );
        }
    }

    _sortLevels(levels: ILevel[]) {
        const sortedLevels: ILevel[] = [];
        if (levels.length > 0) {
            const rootLevelIndex = levels.findIndex((level: ILevel) => level.dependsOnId === undefined || level.dependsOnId === null);
            sortedLevels.unshift(rootLevelIndex === -1 ? levels.pop() : levels.splice(rootLevelIndex, 1)[0]);
            while (levels.length > 0) {
                const nextLevelIndex = levels.findIndex((level: ILevel) => level.dependsOnId === sortedLevels[0].id);
                sortedLevels.unshift(nextLevelIndex === -1 ? levels.pop() : levels.splice(nextLevelIndex, 1)[0]);
            }
        }
        return sortedLevels;
    }

    trackId(index: number, item) {
        return item.id;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
