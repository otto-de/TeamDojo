import { Component, Input, OnInit } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';
import { ILevel } from 'app/shared/model/level.model';
import { IBadge } from 'app/shared/model/badge.model';
import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { IBadgeSkill } from 'app/shared/model/badge-skill.model';

@Component({
    selector: 'jhi-overview-teams',
    templateUrl: './overview-teams.component.html',
    styleUrls: ['./overview-teams.scss']
})
export class OverviewTeamsComponent implements OnInit {
    @Input() teams: ITeam[];
    @Input() levels: ILevel[];
    @Input() badges: IBadge[];

    ngOnInit(): void {}

    onTeamClicked(event, team: ITeam) {
        event.preventDefault();
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
}
