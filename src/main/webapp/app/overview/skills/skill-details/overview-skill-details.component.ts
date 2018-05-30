import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ITeam } from 'app/shared/model/team.model';
import { ISkill } from 'app/shared/model/skill.model';
import { TeamsService } from 'app/teams/teams.service';
import { IBadge } from 'app/shared/model/badge.model';
import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from 'app/entities/level';
import { BadgeService } from 'app/entities/badge';
import { SkillService } from 'app/entities/skill';
import { IBadgeSkill } from 'app/shared/model/badge-skill.model';
import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { sortLevels } from 'app/shared';
import { IAchievableSkill, AchievableSkill } from 'app/shared/model/achievable-skill.model';
import { IComment } from 'app/shared/model/comment.model';
import { TeamsSkillsService } from 'app/teams/teams-skills.service';
import { SkillDetailsInfoComponent } from 'app/teams/skill-details/skill-details-info/skill-details-info.component';

@Component({
    selector: 'jhi-overview-skill-details',
    templateUrl: './overview-skill-details.component.html',
    styleUrls: ['./overview-skill-details.scss']
})
export class OverviewSkillDetailsComponent implements OnInit {
    skill: ISkill;

    achievableSkill: IAchievableSkill;

    selectedTeam: ITeam;

    private _comments: IComment[];

    skills: ISkill[];

    skillComments: IComment[];

    achievedByTeams: ITeam[] = [];

    neededForLevels: ILevel[] = [];

    neededForBadges: IBadge[] = [];

    teams: ITeam[];
    levels: ILevel[];
    badges: IBadge[];
    teamSkills: ITeamSkill[];
    levelSkills: ILevelSkill[];
    badgeSkills: IBadgeSkill[];

    @ViewChild(SkillDetailsInfoComponent) skillInfo;

    constructor(
        private route: ActivatedRoute,
        private skillService: SkillService,
        private teamsService: TeamsService,
        private levelService: LevelService,
        private badgeService: BadgeService,
        private teamsSkillsService: TeamsSkillsService
    ) {}

    ngOnInit(): void {
        this.route.data.subscribe(
            ({ teams, levels, badges, teamSkills, levelSkills, badgeSkills, skill, comments, selectedTeam, skills }) => {
                this.achievedByTeams = [];
                this.neededForLevels = [];
                this.neededForBadges = [];
                this.skill = skill.body;
                this.skills = skills.body ? skills.body : skills;

                this.teams = teams.body;
                this.levels = levels.body;
                this.badges = badges.body;
                this.teamSkills = teamSkills.body;
                this.levelSkills = levelSkills.body;
                this.badgeSkills = badgeSkills.body;
                this.selectedTeam = selectedTeam !== null && selectedTeam.body ? selectedTeam.body : selectedTeam;

                this._comments = comments.body ? comments.body : comments;
                this._mapCommentAuthors();

                const groupedTeamSkills = {};
                this.teamSkills.forEach(teamSkill => {
                    groupedTeamSkills[teamSkill.teamId] = groupedTeamSkills[teamSkill.teamId] || [];
                    groupedTeamSkills[teamSkill.teamId].push(Object.assign(teamSkill));
                });

                const groupedLevelSkills = {};
                this.levelSkills.forEach(levelSkill => {
                    groupedLevelSkills[levelSkill.levelId] = groupedLevelSkills[levelSkill.levelId] || [];
                    groupedLevelSkills[levelSkill.levelId].push(Object.assign(levelSkill));
                });

                const groupedLevels = {};
                this.levels.forEach(level => {
                    groupedLevels[level.dimensionId] = groupedLevels[level.dimensionId] || [];
                    groupedLevels[level.dimensionId].push(Object.assign(level, { skills: groupedLevelSkills[level.id] }));
                    if (level.skills.some(s => s.skillId === this.skill.id)) {
                        this.neededForLevels.push(level);
                    }
                });
                for (const dimensionId in groupedLevels) {
                    if (groupedLevels.hasOwnProperty(dimensionId)) {
                        groupedLevels[dimensionId] = sortLevels(groupedLevels[dimensionId]).reverse();
                    }
                }

                const groupedBadgeSkills = {};
                this.badgeSkills.forEach((badgeSkill: IBadgeSkill) => {
                    groupedBadgeSkills[badgeSkill.badgeId] = groupedBadgeSkills[badgeSkill.badgeId] || [];
                    groupedBadgeSkills[badgeSkill.badgeId].push(Object.assign(badgeSkill));
                });

                this.badges.forEach(badge => {
                    badge.skills = groupedBadgeSkills[badge.id] || [];
                    if (badge.skills.some(s => s.skillId === this.skill.id)) {
                        this.neededForBadges.push(badge);
                    }
                });

                this.teams.forEach(team => {
                    team.skills = groupedTeamSkills[team.id] || [];
                    team.participations.forEach(dimension => {
                        dimension.levels = groupedLevels[dimension.id] || [];
                    });
                });

                this.achievableSkill = new AchievableSkill();
                this.achievableSkill.skillId = this.skill.id;
                this.teamsSkillsService.findAchievableSkill(this.selectedTeam.id, this.skill.id).subscribe(aSkill => {
                    this.achievableSkill = aSkill;
                    this.skillComments = this._getSkillComments();
                });
            }
        );
    }

    onSkillInListChange(skillObjs) {
        this.skill = skillObjs.iSkill;
    }

    onSkillSelected(skillObjs) {
        this.skill = skillObjs.iSkill;
    }

    onCommentSubmitted(newComment: IComment) {
        if (newComment) {
            this._comments.push(newComment);
            this._mapCommentAuthors();
            this.skillComments = this._getSkillComments();
        }
    }

    private _mapCommentAuthors() {
        (this._comments || [])
            .filter((comment: IComment) => comment.author === undefined || Object.keys(comment.author).length === 0)
            .forEach((comment: IComment) => {
                comment.author = (this.teams || []).find((t: ITeam) => t.id === comment.teamId) || {};
            });
    }

    private _getSkillComments(): IComment[] {
        return (this._comments || [])
            .filter(comment => comment.skillId === this.skill.id)
            .sort((comment1, comment2) => comment1.creationDate.diff(comment2.creationDate));
    }
}
