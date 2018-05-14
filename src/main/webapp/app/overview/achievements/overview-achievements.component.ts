import { Component, Input, OnInit } from '@angular/core';
import { ILevel } from 'app/shared/model/level.model';
import { DimensionService } from 'app/entities/dimension';
import { IDimension } from 'app/shared/model/dimension.model';
import { HttpResponse } from '@angular/common/http';
import { IBadge } from 'app/shared/model/badge.model';
import { ITeam } from 'app/shared/model/team.model';
import { IBadgeSkill } from 'app/shared/model/badge-skill.model';
import { ILevelSkill } from 'app/shared/model/level-skill.model';

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

    constructor(private dimensionService: DimensionService) {}

    ngOnInit(): void {
        this.generalBadges = [];
        this.dimensionService.query().subscribe((res: HttpResponse<IDimension[]>) => {
            this.dimensions = res.body;
            const levelsByDimension = {};
            this.levels.forEach((level: ILevel) => {
                levelsByDimension[level.dimensionId] = levelsByDimension[level.dimensionId] || [];
                levelsByDimension[level.dimensionId].push(Object.assign(level));
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
                dimension.levels = levelsByDimension[dimension.id] || [];
                dimension.badges = badgesByDimensionId[dimension.id] || [];
            });
        });
    }

    getProgress(item: ILevel | IBadge) {
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
        let score = 0;
        let totalScore = 0;
        for (const itemSkill of item.skills) {
            totalScore += itemSkill.score;
            if (this.isSkillCompleted(team, itemSkill)) {
                score += itemSkill.score;
            }
        }
        const requiredScore = totalScore * item.requiredScore;
        return score >= requiredScore;
    }

    private isSkillCompleted(team: ITeam, itemSkill: ILevelSkill | IBadgeSkill): boolean {
        return team.skills.some(teamSkill => {
            if (teamSkill.skillId === itemSkill.skillId) {
                return !!teamSkill.completedAt;
            }
            return false;
        });
    }

    private isRelevant(team: ITeam, item: ILevel | IBadge) {
        if ((<ILevel>item).dimensionId) {
            return this.isLevelRelevant(team, item);
        }
        return this.isBadgeRelevant(team, item);
    }

    private isLevelRelevant(team: ITeam, level: ILevel) {
        if (!team.participations.length) {
            return false;
        }
        return team.participations.some((dimension: IDimension) => dimension.id === level.dimensionId);
    }

    private isBadgeRelevant(team: ITeam, badge: IBadge) {
        if (!badge.dimensions || !badge.dimensions.length) {
            return true;
        }
        if (!team.participations.length) {
            return false;
        }
        let badgeDimensionIds = badge.dimensions.map((badgeDim: IDimension) => badgeDim.id);
        return team.participations.some((dimension: IDimension) => badgeDimensionIds.indexOf(dimension.id) !== -1);
    }
}
