import { ActivatedRouteSnapshot, Resolve, Route, RouterStateSnapshot } from '@angular/router';

import { TeamsComponent } from './';
import { TeamsService } from 'app/teams/teams.service';
import { Team } from 'app/shared/model/team.model';
import { Injectable } from '@angular/core';
import { UserRouteAccessService } from 'app/core';

@Injectable()
export class TeamsResolve implements Resolve<any> {
    constructor(private service: TeamsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const shortName = route.params['shortName'] ? route.params['shortName'] : null;
        if (shortName) {
            return this.service.query({ 'shortName.equals': shortName });
        }
        return new Team();
    }
}

export const TEAMS_ROUTE: Route = {
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
};
