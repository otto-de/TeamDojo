import { ActivatedRouteSnapshot, Resolve, Route, RouterStateSnapshot } from '@angular/router';
import { OverviewComponent } from './';
import { Injectable } from '@angular/core';
import { LevelSkillService } from 'app/entities/level-skill';
import { TeamService } from 'app/entities/team';
import { LevelService } from 'app/entities/level';
import { BadgeService } from 'app/entities/badge';
import { TeamSkillService } from 'app/entities/team-skill';
import { BadgeSkillService } from 'app/entities/badge-skill';

@Injectable()
export class AllTeamsResolve implements Resolve<any> {
    constructor(private teamService: TeamService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.teamService.query();
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

export const OVERVIEW_ROUTE: Route = {
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
        teamSkills: AllTeamSkillsResolve,
        levelSkills: AllLevelSkillsResolve,
        badgeSkills: AllBadgeSkillsResolve
    }
};
