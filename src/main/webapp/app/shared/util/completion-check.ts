import { IBadge } from 'app/shared/model/badge.model';
import { ILevel } from 'app/shared/model/level.model';
import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { IBadgeSkill } from 'app/shared/model/badge-skill.model';
import { ITeam } from 'app/shared/model/team.model';
import { IProgress, Progress } from 'app/shared/achievement/model/progress.model';
import { ITeamSkill } from 'app/shared/model/team-skill.model';

export interface ScoreStruct {
    score: number;
    totalScore: number;
}

export class CompletionCheck {
    constructor(private team: ITeam, private item: ILevel | IBadge) {}

    public isCompleted(): boolean {
        return this.getProgress().isCompleted();
    }

    public getProgress(): IProgress {
        const scoreStruct = this.getScore();
        const requiredScore = scoreStruct.totalScore * this.item.requiredScore;
        return new Progress(scoreStruct.score, requiredScore);
    }

    public getScore(): ScoreStruct {
        let score = 0;
        let totalScore = 0;
        if (this.item.skills) {
            for (const itemSkill of this.item.skills) {
                const teamSkill: ITeamSkill = this.findTeamSkill(itemSkill);
                if (teamSkill && teamSkill.irrelevant) {
                    continue;
                }
                totalScore += itemSkill.score;
                if (this.isTeamSkillCompleted(teamSkill)) {
                    score += itemSkill.score;
                }
            }
        }
        return { score: score, totalScore: totalScore };
    }

    private isTeamSkillCompleted(teamSkill: ITeamSkill): boolean {
        return teamSkill && !!teamSkill.completedAt;
    }

    private findTeamSkill(itemSkill: ILevelSkill | IBadgeSkill): ITeamSkill {
        return this.team.skills ? this.team.skills.find((s: ITeamSkill) => s.skillId === itemSkill.skillId) : null;
    }
}
