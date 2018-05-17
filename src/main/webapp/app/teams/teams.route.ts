import { ActivatedRouteSnapshot, Resolve, Route, Router, RouterStateSnapshot } from '@angular/router';

import { TeamsComponent } from './';
import { TeamsService } from 'app/teams/teams.service';
import { Team } from 'app/shared/model/team.model';
import { Injectable } from '@angular/core';
import { UserRouteAccessService } from 'app/core';
import { SkillDetailsComponent } from 'app/teams/skill-details/skill-details.component';
import { Skill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';

@Injectable()
export class TeamsResolve implements Resolve<any> {
    constructor(private service: TeamsService, private router: Router) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const shortName = route.params['shortName'] ? route.params['shortName'] : null;
        if (shortName) {
            return this.service.query({ 'shortName.equals': shortName }).map(value => {
                if (value.body.length === 0) {
                    this.router.navigate(['/error']);
                }
                return value;
            });
        }
        return new Team();
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
            team: TeamsResolve
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
            team: TeamsResolve,
            skill: SkillResolve
        },
        data: {
            authorities: [],
            pageTitle: 'teamdojoApp.teams.skills.title'
        }
    }
];
