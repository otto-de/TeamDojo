import { Route } from '@angular/router';
import { OverviewComponent } from './';

export const OVERVIEW_ROUTE: Route = {
    path: '',
    component: OverviewComponent,
    data: {
        authorities: [],
        pageTitle: 'teamdojoApp.teams.home.title'
    }
};
