import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/index';
import { Report } from 'app/shared/model/report.model';
import { FeedbackService } from './feedback.service';
import { FeedbackComponent } from './feedback.component';

@Injectable()
export class FeedbackResolve implements Resolve<any> {
    constructor(private service: FeedbackService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Report();
    }
}

export const feedbackRoute: Routes = [
    {
        path: 'feedback',
        component: FeedbackComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.feedback.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
