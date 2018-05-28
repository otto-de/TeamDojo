import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { Report } from 'app/shared/model/report.model';
import { FeedbackComponent } from './feedback.component';
import { UserRouteAccessService } from 'app/core';

@Injectable()
export class FeedbackResolve implements Resolve<Report> {
    constructor() {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return new Report();
    }
}

export const feedbackRoute: Routes = [
    {
        path: 'feedback',
        component: FeedbackComponent,
        data: {
            authorities: [],
            pageTitle: 'teamdojoApp.feedback.home.title'
        },
        resolve: {
            report: FeedbackResolve
        },
        canActivate: [UserRouteAccessService]
    }
];
