import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
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

@Component({
    selector: 'jhi-overview-skill-details',
    templateUrl: './overview-skill-details.component.html',
    styleUrls: ['./overview-skill-details.scss']
})
export class OverviewSkillDetailsComponent implements OnInit {
    skill: ISkill;

    achievedByTeams: ITeam[] = [];

    neededForLevels: ILevel[] = [];

    neededForBadges: IBadge[] = [];

    teams: ITeam[];
    levels: ILevel[];
    badges: IBadge[];
    teamSkills: ITeamSkill[];
    levelSkills: ILevelSkill[];
    badgeSkills: IBadgeSkill[];

    // @ViewChild(TeamsSkillsComponent) skillList;

    constructor(
        private route: ActivatedRoute,
        private skillService: SkillService,
        private teamsService: TeamsService,
        private levelService: LevelService,
        private badgeService: BadgeService
    ) {}

    ngOnInit(): void {
        this.route.data.subscribe(({ teams, levels, badges, teamSkills, levelSkills, badgeSkills, skill }) => {
            this.skill = skill;

            this.teams = teams.body;
            this.levels = levels.body;
            this.badges = badges.body;
            this.teamSkills = teamSkills.body;
            this.levelSkills = levelSkills.body;
            this.badgeSkills = badgeSkills.body;

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
            });

            this.teams.forEach(team => {
                team.skills = groupedTeamSkills[team.id] || [];
                team.participations.forEach(dimension => {
                    dimension.levels = groupedLevels[dimension.id] || [];
                });
            });
        });
        this.loadData();
    }

    loadData() {
        this.achievedByTeams = [];
        this.neededForLevels = [];
        this.neededForBadges = [];

        this.skillService.find(this.skill.id).subscribe(skill => {
            this.skill = skill.body;
        });

        this.levelService.query({ 'skillsId.in': this.skill.id }).subscribe(res => {
            this.neededForLevels = res.body;
        });

        this.badgeService.query({ 'skillsId.in': this.skill.id }).subscribe(res => {
            this.neededForBadges = res.body;
        });
    }

    onSkillInListChange(skillObjs) {
        this.skill = skillObjs.aSkill;
        this.skill = skillObjs.iSkill;
    }

    onSkillSelected(skillObjs) {
        this.skill = skillObjs.aSkill;
        this.skill = skillObjs.iSkill;
    }
}
