import { IBadge } from 'app/shared/model/badge.model';
import { ILevel } from 'app/shared/model/level.model';
import { ITeam } from 'app/shared/model/team.model';
import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { ISkill } from 'app/shared/model/skill.model';
import { CompletionCheck, RelevanceCheck } from 'app/shared';

export class TeamScoreCalculation {
    static calcTeamScore(team: ITeam, skills: ISkill[], badges: IBadge[]): number {
        let score = this._calcSkillScore(team, skills);
        score += this._calcLevelBonus(team, skills);
        score += this._calcBadgeBonus(team, badges, skills);
        return score;
    }

    private static _calcSkillScore(team: ITeam, skills: ISkill[]): number {
        let score = 0;
        (skills || []).forEach((skill: ISkill) => {
            if (this._isSkillCompleted(team, skill)) {
                score += skill.score;
            }
        });
        return score;
    }

    private static _calcLevelBonus(team: ITeam, skills: ISkill[]): number {
        let score = 0;
        (team.participations || []).forEach(dimension => {
            (dimension.levels || []).forEach((level: ILevel) => {
                score += this._getBonus(team, level, skills);
            });
        });
        return score;
    }

    private static _calcBadgeBonus(team: ITeam, badges: IBadge[], skills: ISkill[]): number {
        let score = 0;
        (badges || []).forEach((badge: IBadge) => {
            if (new RelevanceCheck(team).isRelevantLevelOrBadge(badge)) {
                score += this._getBonus(team, badge, skills);
            }
        });
        return score;
    }

    private static _isSkillCompleted(team: ITeam, skill: ISkill): boolean {
        const teamSkill = (team.skills || []).find((ts: ITeamSkill) => ts.skillId === skill.id);
        return !!(teamSkill && (teamSkill.skillStatus === 'ACHIEVED' || teamSkill.skillStatus === 'EXPIRING'));
    }

    private static _getBonus(team: ITeam, item: ILevel | IBadge, skills: ISkill[]): number {
        if (!item.instantMultiplier && !item.completionBonus) {
            return 0;
        }
        const levelProgress = new CompletionCheck(team, item, skills).getProgress();
        let score = levelProgress.achieved * item.instantMultiplier;
        if (levelProgress.isCompleted()) {
            score += item.completionBonus;
        }
        return score;
    }
}
