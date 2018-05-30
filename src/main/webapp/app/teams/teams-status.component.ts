import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { IBadge } from 'app/shared/model/badge.model';
import { CompletionCheck, RelevanceCheck } from 'app/shared';
import { Router } from '@angular/router';
import { HighestLevel, IHighestLevel } from 'app/shared/achievement';
import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { ISkill } from 'app/shared/model/skill.model';
import { TeamScoreCalculation } from 'app/shared/util/team-score-calculation';

@Component({
    selector: 'jhi-teams-status',
    templateUrl: './teams-status.component.html',
    styleUrls: ['teams-status.scss']
})
export class TeamsStatusComponent implements OnInit, OnChanges {
    @Input() team: ITeam;
    @Input() teamSkills: ITeamSkill[];
    @Input() badges: IBadge[];
    @Input() skills: ISkill[];
    completedBadges: IBadge[];
    highestAchievedLevels: IHighestLevel[];
    teamScore: number;

    constructor(private router: Router) {}

    ngOnInit(): void {
        this.team.skills = this.teamSkills;
        this.calculateStatus();
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.team.skills = this.teamSkills;
        this.calculateStatus();
    }

    private hasTeamChanged(team: any) {
        return team && team.previousValue && team.previousValue.id !== team.currentValue.id;
    }

    private calculateStatus() {
        this.teamScore = TeamScoreCalculation.calcTeamScore(this.team, this.skills, this.badges);
        this.completedBadges = this.getCompletedBadges();
        this.highestAchievedLevels = this.getHighestAchievedLevels();
    }

    selectItem(itemType: string, id: number) {
        this.router.navigate(['teams', this.team.shortName], {
            queryParams: { [itemType]: id }
        });
    }

    private getCompletedBadges() {
        return this.badges.filter(
            (badge: IBadge) =>
                new RelevanceCheck(this.team).isRelevantBadge(badge) && new CompletionCheck(this.team, badge, this.skills).isCompleted()
        );
    }

    private isLevelCompleted(level) {
        return new CompletionCheck(this.team, level, this.skills).isCompleted();
    }

    private getHighestAchievedLevels(): IHighestLevel[] {
        const highestAchievedLevels = [];
        this.team.participations.forEach((dimension: IDimension) => {
            let ordinal = 0;
            let achievedLevel;
            for (const level of dimension.levels || []) {
                if (!this.isLevelCompleted(level)) {
                    break;
                }
                achievedLevel = level;
                ordinal++;
            }
            if (achievedLevel) {
                highestAchievedLevels.push(new HighestLevel(dimension, achievedLevel, ordinal));
            }
        });
        return highestAchievedLevels;
    }
}
