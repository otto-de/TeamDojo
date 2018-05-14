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
import { sortLevels } from 'app/shared';

@Component({
    selector: 'jhi-teams-achievements',
    templateUrl: './teams-achievements.component.html',
    styleUrls: ['teams-achievements.scss']
})
export class TeamsAchievementsComponent implements OnInit {
    CSS_ID_DIMENSION_PREFIX = 'achievements-dimension';
    CSS_ID_LEVEL_PREFIX = 'achievements-level';
    @Input() team: ITeam;
    badges: IBadge[];
    levels: { [key: number]: ILevel[] };
    activeDimensionCssId: string;
    private defaultDimensionCssId: string;
    levelCssId: string;

    constructor(
        private route: ActivatedRoute,
        private teamsAchievementsService: TeamsAchievementsService,
        private jhiAlertService: JhiAlertService
    ) {
        this.badges = [];
        this.levels = {};
        this.defaultDimensionCssId = '';
    }

    ngOnInit() {
        if (this.team.participations && this.team.participations[0]) {
            this.defaultDimensionCssId = `${this.CSS_ID_DIMENSION_PREFIX}-${this.team.participations[0].id}`;
        }
        this.activeDimensionCssId = this.defaultDimensionCssId;
        this.route.paramMap.subscribe((params: ParamMap) => {
            const dimensionId = params.get('dimension');
            this.levelCssId = params.get('level') ? `${this.CSS_ID_LEVEL_PREFIX}-${params.get('level')}` : '';
            this.activeDimensionCssId = dimensionId ? `${this.CSS_ID_DIMENSION_PREFIX}-${dimensionId}` : this.defaultDimensionCssId;
            this.onDimensionChange(this.activeDimensionCssId);
        });
        this.loadAll();
    }

    onDimensionChange(panelId: string) {
        if (panelId === this.activeDimensionCssId && this.levelCssId && document.getElementById(this.levelCssId)) {
            document.getElementById(this.levelCssId).scrollIntoView({ block: 'end', behavior: 'smooth' });
        }
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
