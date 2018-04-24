import { Route } from '@angular/router';

import { TeamsComponent } from './';

export const TEAMS_ROUTE: Route = {
    path: 'teams/:shortName',
    component: TeamsComponent,
    data: {
        authorities: [],
        pageTitle: 'teams.name'
    }
};
