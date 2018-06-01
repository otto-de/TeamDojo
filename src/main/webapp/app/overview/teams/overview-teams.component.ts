import { Component, Input, OnInit } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';
import { ILevel } from 'app/shared/model/level.model';
import { IBadge } from 'app/shared/model/badge.model';
import { CompletionCheck } from 'app/shared/util/completion-check';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { RelevanceCheck } from 'app/shared';
import { IDimension } from 'app/shared/model/dimension.model';
import { TeamScore } from 'app/shared/model/team-score.model';
import 'simplebar';
import { ISkill } from 'app/shared/model/skill.model';
import { TeamScoreCalculation } from 'app/shared/util/team-score-calculation';

@Component({
    selector: 'jhi-overview-teams',
    templateUrl: './overview-teams.component.html',
    styleUrls: ['./overview-teams.scss']
})
export class OverviewTeamsComponent implements OnInit {
    @Input() teams: ITeam[];
    @Input() levels: ILevel[];
    @Input() badges: IBadge[];
    @Input() skills: ISkill[];
    private filtered: boolean;
    private relevantTeamIds: number[];
    private completedTeamIds: number[];
    teamScores: TeamScore[];

    constructor(private route: ActivatedRoute) {}

    ngOnInit(): void {
        this.teamScores = [];
        this.route.queryParamMap.subscribe((params: ParamMap) => {
            const badgeId: number = this.getParamAsNumber('badge', params);
            const levelId: number = this.getParamAsNumber('level', params);
            const dimensionId: number = this.getParamAsNumber('dimension', params);

            this.filtered = !!badgeId || !!levelId || !!dimensionId;
            const relevantTeams = this.getRelevantTeams(badgeId, levelId, dimensionId);
            this.completedTeamIds = this.getCompletedTeamIds(relevantTeams, badgeId, levelId, dimensionId);
            this.relevantTeamIds = this.getRelevantTeamIds(relevantTeams);
        });

        for (const team of this.teams) {
            this.teamScores.push(new TeamScore(team, this._calcTeamScore(team)));
        }
        this.teamScores = this.teamScores.sort((ts1, ts2) => {
            if (ts1.score > ts2.score) {
                return 1;
            }
            if (ts1.score < ts2.score) {
                return -1;
            }
            return 0;
        });
        this.teamScores = this.teamScores.reverse();
    }

    private getRelevantTeams(badgeId: number, levelId: number, dimensionId: number) {
        return this.teams.filter((team: ITeam) => {
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
        });
    }

    private getCompletedTeamIds(relevantTeams, badgeId: number, levelId: number, dimensionId: number) {
        return relevantTeams
            .filter((team: ITeam) => {
                if (badgeId) {
                    const badge = this.badges.find((b: IBadge) => b.id === badgeId);
                    return new CompletionCheck(team, badge, this.skills).isCompleted();
                } else if (levelId) {
                    const level = this.levels.find((l: ILevel) => l.id === levelId);
                    return new CompletionCheck(team, level, this.skills).isCompleted();
                } else if (dimensionId) {
                    const dimensions = team.participations.find((d: IDimension) => d.id === dimensionId);
                    return dimensions.levels.every((level: ILevel) => new CompletionCheck(team, level, this.skills).isCompleted());
                }
                return false;
            })
            .map((team: ITeam) => team.id);
    }

    private getRelevantTeamIds(relevantTeams) {
        return relevantTeams.map((team: ITeam) => team.id);
    }

    showAsComplete(team: ITeam): boolean {
        return this.filtered && this.isRelevant(team) && this.isCompleted(team);
    }

    showAsIncomplete(team: ITeam): boolean {
        return this.filtered && this.isRelevant(team) && !this.isCompleted(team);
    }

    showAsIrrelevant(team: ITeam): boolean {
        return this.filtered && !this.isRelevant(team);
    }

    private isRelevant(team: ITeam): boolean {
        return this.relevantTeamIds.indexOf(team.id) !== -1;
    }

    private isCompleted(team: ITeam): boolean {
        return this.completedTeamIds.indexOf(team.id) !== -1;
    }

    calcTotalCompletedLevel() {
        let totalCompletedLevel = 0;
        for (const team of this.teams) {
            totalCompletedLevel += this.calcCompletedLevel(team);
        }
        return totalCompletedLevel;
    }

    calcTotalCompletedBadges() {
        let totalCompletedBadges = 0;
        for (const team of this.teams) {
            totalCompletedBadges += this.calcCompletedBadges(team);
        }
        return totalCompletedBadges;
    }

    calcTotalTeamScore() {
        let totalTeamScore = 0;
        for (const team of this.teams) {
            totalTeamScore += this._calcTeamScore(team);
        }
        return totalTeamScore;
    }

    getTotalLevelBase() {
        let totalLevelBase = 0;
        this.teams.forEach((team: ITeam) => {
            team.participations.forEach((dimension: IDimension) => {
                totalLevelBase += dimension.levels.length;
            });
        });
        return totalLevelBase;
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

    private _calcTeamScore(team: ITeam) {
        return TeamScoreCalculation.calcTeamScore(team, this.skills, this.badges);
    }

    private isLevelOrBadgeCompleted(team: ITeam, item: ILevel | IBadge): boolean {
        return new CompletionCheck(team, item, this.skills).isCompleted();
    }

    private getParamAsNumber(name: string, params: ParamMap): number {
        return Number.parseInt(params.get(name));
    }
}
