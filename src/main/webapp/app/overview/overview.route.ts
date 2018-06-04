import { ActivatedRouteSnapshot, Resolve, Route, Router, RouterStateSnapshot } from '@angular/router';
import { OverviewComponent } from './';
import { Injectable } from '@angular/core';
import { LevelSkillService } from 'app/entities/level-skill';
import { TeamService } from 'app/entities/team';
import { LevelService } from 'app/entities/level';
import { BadgeService } from 'app/entities/badge';
import { TeamSkillService } from 'app/entities/team-skill';
import { BadgeSkillService } from 'app/entities/badge-skill';
import { OverviewSkillDetailsComponent } from 'app/overview/skills/skill-details/overview-skill-details.component';
import { Skill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { CommentService } from 'app/entities/comment';
import { TeamsSelectionResolve } from 'app/shared/teams-selection/teams-selection.resolve';
import { Observable } from 'rxjs/Observable';
import { sortLevels } from 'app/shared';

@Injectable()
export class AllTeamsResolve implements Resolve<any> {
    constructor(
        private teamService: TeamService,
        private teamSkillService: TeamSkillService,
        private levelService: LevelService,
        private levelSkillService: LevelSkillService,
        private badgeSkillService: BadgeSkillService,
        private badgeService: BadgeService
    ) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return Observable.combineLatest(
            this.teamService.query(),
            this.teamSkillService.query(),
            this.levelService.query(),
            this.levelSkillService.query(),
            this.badgeService.query(),
            this.badgeSkillService.query()
        ).map(([teamsRes, teamSkillsRes, levelsRes, levelSkillsRes, badgesRes, badgeSkillsRes]) => {
            const teams = teamsRes.body || [];
            const teamSkills = teamSkillsRes.body || [];
            const levels = levelsRes.body || [];
            const levelSkills = levelSkillsRes.body || [];
            const badges = badgesRes.body || [];
            const badgeSkills = badgeSkillsRes.body || [];

            const groupedTeamSkills = {};
            teamSkills.forEach(teamSkill => {
                groupedTeamSkills[teamSkill.teamId] = groupedTeamSkills[teamSkill.teamId] || [];
                groupedTeamSkills[teamSkill.teamId].push(Object.assign(teamSkill));
            });

            const groupedLevelSkills = {};
            levelSkills.forEach(levelSkill => {
                groupedLevelSkills[levelSkill.levelId] = groupedLevelSkills[levelSkill.levelId] || [];
                groupedLevelSkills[levelSkill.levelId].push(Object.assign(levelSkill));
            });

            const groupedLevels = {};
            levels.forEach(level => {
                groupedLevels[level.dimensionId] = groupedLevels[level.dimensionId] || [];
                groupedLevels[level.dimensionId].push(Object.assign(level, { skills: groupedLevelSkills[level.id] }));
            });
            for (const dimensionId in groupedLevels) {
                if (groupedLevels.hasOwnProperty(dimensionId)) {
                    groupedLevels[dimensionId] = sortLevels(groupedLevels[dimensionId]).reverse();
                }
            }

            const groupedBadgeSkills = {};
            badgeSkills.forEach(badgeSkill => {
                groupedBadgeSkills[badgeSkill.badgeId] = groupedBadgeSkills[badgeSkill.badgeId] || [];
                groupedBadgeSkills[badgeSkill.badgeId].push(Object.assign(badgeSkill));
            });

            badges.forEach(badge => {
                badge.skills = groupedBadgeSkills[badge.id] || [];
            });

            teams.forEach(team => {
                team.skills = groupedTeamSkills[team.id] || [];
                team.participations.forEach(dimension => {
                    dimension.levels = groupedLevels[dimension.id] || [];
                });
            });
            return { body: teams };
        });
    }
}

@Injectable()
export class AllLevelsResolve implements Resolve<any> {
    constructor(private levelService: LevelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.levelService.query();
    }
}

@Injectable()
export class AllBadgesResolve implements Resolve<any> {
    constructor(private badgeService: BadgeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.badgeService.query();
    }
}

@Injectable()
export class AllTeamSkillsResolve implements Resolve<any> {
    constructor(private teamSkillService: TeamSkillService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.teamSkillService.query();
    }
}

@Injectable()
export class AllLevelSkillsResolve implements Resolve<any> {
    constructor(private levelSkillService: LevelSkillService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.levelSkillService.query();
    }
}

@Injectable()
export class AllBadgeSkillsResolve implements Resolve<any> {
    constructor(private badgeSkillService: BadgeSkillService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.badgeSkillService.query();
    }
}

@Injectable()
export class AllSkillsResolve implements Resolve<any> {
    constructor(private skillService: SkillService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.skillService.query();
    }
}

@Injectable()
export class AllCommentsResolve implements Resolve<any> {
    constructor(private commentService: CommentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.commentService.query();
    }
}

@Injectable()
export class SkillResolve implements Resolve<any> {
    constructor(private skillService: SkillService, private router: Router) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const skillId = route.params['skillId'] ? route.params['skillId'] : null;
        if (skillId) {
            return this.skillService.find(skillId);
        }
        return new Skill();
    }
}

export const OVERVIEW_ROUTE: Route[] = [
    {
        path: '',
        component: OverviewComponent,
        data: {
            authorities: [],
            pageTitle: 'teamdojoApp.teams.home.title'
        },
        resolve: {
            teams: AllTeamsResolve,
            levels: AllLevelsResolve,
            badges: AllBadgesResolve,
            skills: AllSkillsResolve,
            selectedTeam: TeamsSelectionResolve
        }
    },
    {
        path: 'overview/skills/:skillId',
        component: OverviewSkillDetailsComponent,
        resolve: {
            teams: AllTeamsResolve,
            levels: AllLevelsResolve,
            badges: AllBadgesResolve,
            teamSkills: AllTeamSkillsResolve,
            levelSkills: AllLevelSkillsResolve,
            badgeSkills: AllBadgeSkillsResolve,
            skill: SkillResolve,
            comments: AllCommentsResolve,
            selectedTeam: TeamsSelectionResolve,
            skills: AllSkillsResolve
        },
        data: {
            authorities: [],
            pageTitle: 'teamdojoApp.teams.skills.title'
        }
    }
];
