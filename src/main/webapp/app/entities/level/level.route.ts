import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Level } from 'app/shared/model/level.model';
import { LevelService } from './level.service';
import { LevelComponent } from './level.component';
import { LevelDetailComponent } from './level-detail.component';
import { LevelUpdateComponent } from './level-update.component';
import { LevelDeletePopupComponent } from './level-delete-dialog.component';

@Injectable()
export class LevelResolve implements Resolve<any> {
    constructor(private service: LevelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Level();
    }
}

export const levelRoute: Routes = [
    {
        path: 'level',
        component: LevelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Levels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'level/:id/view',
        component: LevelDetailComponent,
        resolve: {
            level: LevelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Levels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'level/new',
        component: LevelUpdateComponent,
        resolve: {
            level: LevelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Levels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'level/:id/edit',
        component: LevelUpdateComponent,
        resolve: {
            level: LevelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Levels'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const levelPopupRoute: Routes = [
    {
        path: 'level/:id/delete',
        component: LevelDeletePopupComponent,
        resolve: {
            level: LevelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Levels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
