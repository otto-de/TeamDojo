import { sortLevels } from 'app/shared/index';
import { BadgeService } from 'app/entities/badge';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { TeamSkillService } from 'app/entities/team-skill';
import { SkillService } from 'app/entities/skill';
import { LevelService } from 'app/entities/level';
import { LevelSkillService } from 'app/entities/level-skill';
import { Observable } from 'rxjs/Observable';
import { BadgeSkillService } from 'app/entities/badge-skill';
import { Skill } from 'app/shared/model/skill.model';
import { CommentService } from 'app/entities/comment';
import { TeamService } from 'app/entities/team';
import { Injectable } from '@angular/core';
import { TeamsService } from 'app/teams/teams.service';

@Injectable()
export class AllTeamsResolve implements Resolve<any> {
    constructor(private teamService: TeamService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.teamService.query();
    }
}

@Injectable()
export class DojoModelResolve implements Resolve<any> {
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

            const groupedBadges = {};
            badges.forEach(badge => {
                (badge.dimensions || []).forEach(dimension => {
                    groupedBadges[dimension.id] = groupedBadges[dimension.id] || [];
                    groupedBadges[dimension.id].push(Object.assign(badge, { skills: groupedBadgeSkills[badge.id] }));
                });
            });

            teams.forEach(team => {
                team.skills = groupedTeamSkills[team.id] || [];
                team.participations.forEach(dimension => {
                    dimension.levels = groupedLevels[dimension.id] || [];
                    dimension.badges = groupedBadges[dimension.id] || [];
                });
            });
            return { teams, teamSkills, levels, levelSkills, badges, badgeSkills };
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
            return this.skillService.query({ 'id.equals': skillId }).map(res => {
                if (res.body.length === 0) {
                    this.router.navigate(['/error']);
                }
                return res.body[0];
            });
        }
        return new Skill();
    }
}
