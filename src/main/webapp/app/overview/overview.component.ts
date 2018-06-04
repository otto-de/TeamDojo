import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ITeam } from 'app/shared/model/team.model';
import { ILevel } from 'app/shared/model/level.model';
import { IBadge } from 'app/shared/model/badge.model';
import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { sortLevels } from 'app/shared';
import { IBadgeSkill } from 'app/shared/model/badge-skill.model';
import { ISkill } from 'app/shared/model/skill.model';

@Component({
    selector: 'jhi-overview',
    templateUrl: './overview.component.html',
    styleUrls: ['./overview.scss']
})
export class OverviewComponent implements OnInit {
    teams: ITeam[];
    levels: ILevel[];
    badges: IBadge[];
    skills: ISkill[];
    teamSkills: ITeamSkill[];
    levelSkills: ILevelSkill[];
    badgeSkills: IBadgeSkill[];
    selectedTeam: ITeam;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ teams, levels, badges, teamSkills, levelSkills, badgeSkills, skills, selectedTeam }) => {
            this.teams = teams.body;
            this.levels = levels.body;
            this.badges = badges.body;
            this.teamSkills = teamSkills.body;
            this.levelSkills = levelSkills.body;
            this.badgeSkills = badgeSkills.body;
            this.skills = skills.body;
            this.selectedTeam = selectedTeam && selectedTeam.body ? selectedTeam.body : {};

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
    }
}
