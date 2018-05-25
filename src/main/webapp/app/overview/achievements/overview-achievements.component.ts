import { Component, Input, OnInit } from '@angular/core';
import { ILevel } from 'app/shared/model/level.model';
import { DimensionService } from 'app/entities/dimension';
import { IDimension } from 'app/shared/model/dimension.model';
import { HttpResponse } from '@angular/common/http';
import { IBadge } from 'app/shared/model/badge.model';
import { ITeam } from 'app/shared/model/team.model';
import { CompletionCheck } from 'app/shared/util/completion-check';
import { Router } from '@angular/router';
import { RelevanceCheck, sortLevels } from 'app/shared';
import { BreadcrumbService } from 'app/layouts/navbar/breadcrumb.service';
import 'simplebar';

@Component({
    selector: 'jhi-overview-achievements',
    templateUrl: './overview-achievements.component.html',
    styleUrls: ['./overview-achievements.scss']
})
export class OverviewAchievementsComponent implements OnInit {
    @Input() teams: ITeam[];
    @Input() levels: ILevel[];
    @Input() badges: IBadge[];
    dimensions: IDimension[];
    generalBadges: IBadge[];
    activeItemIds: { [key: string]: number };
    expandedDimensions: string[];

    constructor(private dimensionService: DimensionService, private router: Router, private breadcrumbService: BreadcrumbService) {}

    ngOnInit(): void {
        this.activeItemIds = {
            badge: null,
            level: null,
            dimension: null
        };
        this.generalBadges = [];
        this.expandedDimensions = [];
        this.dimensionService.query().subscribe((res: HttpResponse<IDimension[]>) => {
            this.dimensions = res.body;
            const levelsByDimensionId = {};
            this.levels.forEach((level: ILevel) => {
                levelsByDimensionId[level.dimensionId] = levelsByDimensionId[level.dimensionId] || [];
                levelsByDimensionId[level.dimensionId].push(Object.assign(level));
            });

            const badgesByDimensionId = {};
            this.badges.forEach((badge: IBadge) => {
                if (badge.dimensions && badge.dimensions.length) {
                    badge.dimensions.forEach((dimension: IDimension) => {
                        badgesByDimensionId[dimension.id] = badgesByDimensionId[dimension.id] || [];
                        badgesByDimensionId[dimension.id].push(Object.assign(badge));
                    });
                } else {
                    this.generalBadges.push(Object.assign(badge));
                }
            });

            this.dimensions.forEach((dimension: IDimension) => {
                dimension.levels = (sortLevels(levelsByDimensionId[dimension.id]) || []).reverse();
                dimension.badges = badgesByDimensionId[dimension.id] || [];
            });

            this.expandedDimensions = this.dimensions
                ? this.dimensions.map((dimension: IDimension) => `achievements-dimension-${dimension.id}`)
                : [];
        });
    }

    getAchievementProgress(item: ILevel | IBadge) {
        let baseCount = 0;
        let completedCount = 0;
        this.teams.forEach((team: ITeam) => {
            if (this.isRelevant(team, item)) {
                baseCount++;
                if (this.isLevelOrBadgeCompleted(team, item)) {
                    completedCount++;
                }
            }
        });
        return baseCount === 0 ? 0 : completedCount / baseCount * 100;
    }

    private isLevelOrBadgeCompleted(team: ITeam, item: ILevel | IBadge): boolean {
        return new CompletionCheck(team, item).isCompleted();
    }

    private isRelevant(team: ITeam, item: ILevel | IBadge): boolean {
        return new RelevanceCheck(team).isRelevantLevelOrBadge(item);
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
                this.router.navigate(['.']);
            } else {
                this.activeItemIds[itemType] = itemId;
                this.router.navigate(['.'], {
                    queryParams: { [itemType]: this.activeItemIds[itemType] }
                });
            }
        }
    }
}
