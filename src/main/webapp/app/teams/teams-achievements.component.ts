import { Component, Input, OnInit } from '@angular/core';
import { IBadge } from 'app/shared/model/badge.model';
import { ITeam } from 'app/shared/model/team.model';
import { ILevel } from 'app/shared/model/level.model';
import { JhiAlertService } from 'ng-jhipster';
import { TeamsAchievementsService } from './teams-achievements.service';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { IDimension } from 'app/shared/model/dimension.model';

@Component({
    selector: 'jhi-teams-achievements',
    templateUrl: './teams-achievements.component.html',
    styleUrls: ['teams-achievements.scss']
})
export class TeamsAchievementsComponent implements OnInit {
    @Input() team: ITeam;
    @Input() badges: IBadge[];
    generalBadges: IBadge[];
    activeItemIds: { badge: number; level: number; dimension: number };
    expandedDimensions: string[];

    constructor(
        private route: ActivatedRoute,
        private teamsAchievementsService: TeamsAchievementsService,
        private jhiAlertService: JhiAlertService,
        private router: Router
    ) {}

    ngOnInit() {
        this.generalBadges = this.badges.filter((badge: IBadge) => !badge.dimensions || !badge.dimensions.length);
        this.expandedDimensions = [];

        this.route.queryParamMap.subscribe((params: ParamMap) => {
            const dimensionId = this.getParamAsNumber('dimension', params);
            const levelId = this.getParamAsNumber('level', params);
            const badgeId = this.getParamAsNumber('badge', params);
            this.activeItemIds = {
                badge: null,
                level: null,
                dimension: null
            };
            if (dimensionId) {
                const dimension = this.team.participations.find((d: IDimension) => d.id === dimension);
                if (dimension) {
                    this.activeItemIds.dimension = dimensionId;
                    this.setExpandedDimensionId(dimensionId);
                }
            }
            if (levelId) {
                const dimension = this.team.participations.find((d: IDimension) => d.levels.some((l: ILevel) => l.id == levelId));
                if (dimension) {
                    this.activeItemIds.dimension = dimension.id;
                    this.setExpandedDimensionId(dimension.id);
                    const level = dimension.levels.find((level: ILevel) => level.id === levelId);
                    if (level) {
                        this.activeItemIds.level = level.id;
                    }
                }
            } else if (badgeId) {
                const badge = this.generalBadges.find((badge: IBadge) => badge.id === badgeId);
                if (badge) {
                    this.activeItemIds.badge = badge.id;
                }
            } else {
                if (this.team.participations && this.team.participations.length) {
                    this.setExpandedDimensionId(this.team.participations[0].id);
                }
            }
        });
    }

    selectItem(itemType: string, itemId: number) {
        if (itemType && itemId >= 0) {
            for (const availableItemType in this.activeItemIds) {
                if (this.activeItemIds.hasOwnProperty(availableItemType) && availableItemType !== itemType) {
                    this.activeItemIds[availableItemType] = null;
                }
            }
            if (this.activeItemIds[itemType] === itemId) {
                this.activeItemIds[itemType] = null;
                this.router.navigate(['teams', this.team.shortName]);
            } else {
                this.activeItemIds[itemType] = itemId;
                this.router.navigate(['teams', this.team.shortName], {
                    queryParams: { [itemType]: this.activeItemIds[itemType] }
                });
            }
        }
    }

    getAchievementProgress(item: ILevel | IBadge): number {
        return 0;
    }

    private setExpandedDimensionId(dimensionId: number) {
        this.expandedDimensions.push(`achievements-dimension-${dimensionId}`);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    private getParamAsNumber(name: string, params: ParamMap): number {
        return Number.parseInt(params.get(name));
    }
}
