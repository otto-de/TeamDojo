import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Dimension } from 'app/shared/model/dimension.model';
import { DimensionService } from './dimension.service';
import { DimensionComponent } from './dimension.component';
import { DimensionDetailComponent } from './dimension-detail.component';
import { DimensionUpdateComponent } from './dimension-update.component';
import { DimensionDeletePopupComponent } from './dimension-delete-dialog.component';

@Injectable()
export class DimensionResolve implements Resolve<any> {
    constructor(private service: DimensionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Dimension();
    }
}

export const dimensionRoute: Routes = [
    {
        path: 'dimension',
        component: DimensionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dimensions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dimension/:id/view',
        component: DimensionDetailComponent,
        resolve: {
            dimension: DimensionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dimensions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dimension/new',
        component: DimensionUpdateComponent,
        resolve: {
            dimension: DimensionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dimensions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dimension/:id/edit',
        component: DimensionUpdateComponent,
        resolve: {
            dimension: DimensionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dimensions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dimensionPopupRoute: Routes = [
    {
        path: 'dimension/:id/delete',
        component: DimensionDeletePopupComponent,
        resolve: {
            dimension: DimensionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dimensions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
