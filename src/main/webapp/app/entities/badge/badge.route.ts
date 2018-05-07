import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Badge } from 'app/shared/model/badge.model';
import { BadgeService } from './badge.service';
import { BadgeComponent } from './badge.component';
import { BadgeDetailComponent } from './badge-detail.component';
import { BadgeUpdateComponent } from './badge-update.component';
import { BadgeDeletePopupComponent } from './badge-delete-dialog.component';

@Injectable()
export class BadgeResolve implements Resolve<any> {
    constructor(private service: BadgeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Badge();
    }
}

export const badgeRoute: Routes = [
    {
        path: 'badge',
        component: BadgeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.badge.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'badge/:id/view',
        component: BadgeDetailComponent,
        resolve: {
            badge: BadgeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.badge.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'badge/new',
        component: BadgeUpdateComponent,
        resolve: {
            badge: BadgeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.badge.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'badge/:id/edit',
        component: BadgeUpdateComponent,
        resolve: {
            badge: BadgeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.badge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const badgePopupRoute: Routes = [
    {
        path: 'badge/:id/delete',
        component: BadgeDeletePopupComponent,
        resolve: {
            badge: BadgeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.badge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
