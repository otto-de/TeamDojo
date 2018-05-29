import { IBadge } from 'app/shared/model/badge.model';
import { ILevel } from 'app/shared/model/level.model';
import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { IBadgeSkill } from 'app/shared/model/badge-skill.model';
import { ITeam } from 'app/shared/model/team.model';
import { IProgress, Progress } from 'app/shared/achievement/model/progress.model';
import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { ISkill } from 'app/shared/model/skill.model';

export class CompletionCheck {
    constructor(private team: ITeam, private item: ILevel | IBadge, private allSkills: ISkill[]) {}

    public isCompleted(): boolean {
        return this.getProgress().isCompleted();
    }

    public getProgress(): IProgress {
        let score = 0;
        let totalScore = 0;
        if (this.item.skills) {
            for (const itemSkill of this.item.skills) {
                const teamSkill: ITeamSkill = this.findTeamSkill(itemSkill);
                if (teamSkill && teamSkill.irrelevant) {
                    continue;
                }
                const skill = this.findSkill(itemSkill.skillId);
                totalScore += skill.score;
                if (this.isTeamSkillCompleted(teamSkill)) {
                    score += skill.score;
                }
            }
        }
        const requiredScore = totalScore * this.item.requiredScore;
        return new Progress(score, requiredScore, totalScore);
    }

    private isTeamSkillCompleted(teamSkill: ITeamSkill): boolean {
        return teamSkill && !!teamSkill.completedAt;
    }

    private findTeamSkill(itemSkill: ILevelSkill | IBadgeSkill): ITeamSkill {
        return this.team.skills ? this.team.skills.find((s: ITeamSkill) => s.skillId === itemSkill.skillId) : null;
    }

    private findSkill(skillId: number): ISkill {
        return this.allSkills.find(s => s.id === skillId);
    }
}
