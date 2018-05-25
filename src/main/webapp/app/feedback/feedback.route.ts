import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/index';
import { Report } from 'app/shared/model/report.model';
import { FeedbackService } from './feedback.service';
import { FeedbackComponent } from './feedback.component';

export const feedbackRoute: Routes = [
    {
        path: 'feedback',
        component: FeedbackComponent,
        data: {
            authorities: [],
            pageTitle: 'teamdojoApp.feedback.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
