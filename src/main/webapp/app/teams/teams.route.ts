import { ActivatedRouteSnapshot, Resolve, Route, Router, RouterStateSnapshot } from '@angular/router';

import { TeamsComponent } from './';
import { TeamsService } from 'app/teams/teams.service';
import { Team } from 'app/shared/model/team.model';
import { Injectable } from '@angular/core';
import { UserRouteAccessService } from 'app/core';
import { SkillDetailsComponent } from 'app/teams/skill-details/skill-details.component';
import { Skill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { BadgeService } from 'app/entities/badge';
import { LevelService } from 'app/entities/level';
import { TeamSkillService } from 'app/entities/team-skill';
import { BadgeSkillService } from 'app/entities/badge-skill';
import { LevelSkillService } from 'app/entities/level-skill';
import { CommentService } from 'app/entities/comment';
import { TeamsSelectionService } from 'app/teams/teams-selection/teams-selection.service';

@Injectable()
export class TeamAndTeamSkillResolve implements Resolve<any> {
    constructor(private teamService: TeamsService, private teamSkillService: TeamSkillService, private router: Router) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const shortName = route.params['shortName'] ? route.params['shortName'] : null;
        if (shortName) {
            return this.teamService
                .query({
                    'shortName.equals': shortName
                })
                .flatMap(teamResponse => {
                    if (teamResponse.body.length === 0) {
                        this.router.navigate(['/error']);
                    }
                    const team = teamResponse.body[0];
                    return this.teamSkillService.query({ 'teamId.equals': team.id }).map(teamSkillResponse => {
                        team.skills = teamSkillResponse.body;
                        return team;
                    });
                });
        }
        return new Team();
    }
}

@Injectable()
export class AllLevelsResolve implements Resolve<any> {
    constructor(private levelService: LevelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.levelService.query({ eagerload: true });
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
export class AllTeamsResolve implements Resolve<any> {
    constructor(private teamsService: TeamsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.teamsService.query();
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
export class TeamsSelectionResolve implements Resolve<any> {
    constructor(private teamsSelectionService: TeamsSelectionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.teamsSelectionService.selectedTeam;
    }
}

@Injectable()
export class SkillResolve implements Resolve<any> {
    constructor(private skillService: SkillService, private teamsService: TeamsService, private router: Router) {}

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

export const TEAMS_ROUTES: Route[] = [
    {
        path: 'teams/:shortName',
        component: TeamsComponent,
        resolve: {
            team: TeamAndTeamSkillResolve,
            levels: AllLevelsResolve,
            badges: AllBadgesResolve,
            levelSkills: AllLevelSkillsResolve,
            badgeSkills: AllBadgeSkillsResolve,
            skills: AllSkillsResolve
        },
        data: {
            authorities: [],
            pageTitle: 'teamdojoApp.teams.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'teams/:shortName/skills/:skillId',
        component: SkillDetailsComponent,
        resolve: {
            team: TeamAndTeamSkillResolve,
            skill: SkillResolve,
            skills: AllSkillsResolve,
            comments: AllCommentsResolve,
            teams: AllTeamsResolve,
            selectedTeam: TeamsSelectionResolve,
            levels: AllLevelsResolve,
            badges: AllBadgesResolve,
            levelSkills: AllLevelSkillsResolve,
            badgeSkills: AllBadgeSkillsResolve,
            teamSkills: AllTeamSkillsResolve
        },
        data: {
            authorities: [],
            pageTitle: 'teamdojoApp.teams.skills.title'
        }
    }
];
