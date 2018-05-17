import { Component, Input, OnInit } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';
import { ILevel } from 'app/shared/model/level.model';
import { IBadge } from 'app/shared/model/badge.model';
import { CompletionCheck } from 'app/shared/util/completion-check';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { RelevanceCheck } from 'app/shared';

@Component({
    selector: 'jhi-overview-teams',
    templateUrl: './overview-teams.component.html',
    styleUrls: ['./overview-teams.scss']
})
export class OverviewTeamsComponent implements OnInit {
    @Input() teams: ITeam[];
    @Input() levels: ILevel[];
    @Input() badges: IBadge[];
    activeTeamIds: number[];

    constructor(private route: ActivatedRoute) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe((params: ParamMap) => {
            const badgeId: number = this.getParamAsNumber('badge', params);
            const levelId: number = this.getParamAsNumber('level', params);
            const dimensionId: number = this.getParamAsNumber('dimension', params);

            this.activeTeamIds = this.teams
                .filter((team: ITeam) => {
                    const relevanceCheck = new RelevanceCheck(team);
                    if (badgeId) {
                        const badge = this.badges.find((b: IBadge) => b.id === badgeId);
                        return relevanceCheck.isRelevantBadge(badge);
                    } else if (levelId) {
                        const level = this.levels.find((l: ILevel) => l.id === levelId);
                        return relevanceCheck.isRelevantLevel(level);
                    } else if (dimensionId) {
                        return relevanceCheck.isRelevantDimensionId(dimensionId);
                    }
                    return false;
                })
                .map((team: ITeam) => team.id);
        });
    }

    isActive(team: ITeam): boolean {
        return this.activeTeamIds.indexOf(team.id) !== -1;
    }

    isInactive(team: ITeam): boolean {
        return this.activeTeamIds.length && this.activeTeamIds.indexOf(team.id) === -1;
    }

    calcLevelBase(team: ITeam) {
        const relevantDimensionIds = team.participations.map(d => d.id);
        return this.levels.filter(l => relevantDimensionIds.indexOf(l.dimensionId) !== -1).length;
    }

    calcCompletedLevel(team: ITeam) {
        let count = 0;
        team.participations.forEach(dimension => {
            for (const level of dimension.levels) {
                if (!this.isLevelOrBadgeCompleted(team, level)) {
                    break;
                }
                count++;
            }
        });
        return count;
    }

    calcCompletedBadges(team: ITeam) {
        let count = 0;
        this.badges.forEach(badge => {
            if (this.isLevelOrBadgeCompleted(team, badge)) {
                count++;
            }
        });
        return count;
    }

    private isLevelOrBadgeCompleted(team: ITeam, item: ILevel | IBadge): boolean {
        return new CompletionCheck(team, item).isCompleted();
    }

    private getParamAsNumber(name: string, params: ParamMap): number {
        return Number.parseInt(params.get(name));
    }
}
