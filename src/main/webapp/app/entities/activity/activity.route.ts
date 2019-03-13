import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Activity } from 'app/shared/model/activity.model';
import { ActivityService } from './activity.service';
import { ActivityComponent } from './activity.component';
import { ActivityDetailComponent } from './activity-detail.component';
import { ActivityUpdateComponent } from './activity-update.component';
import { ActivityDeletePopupComponent } from './activity-delete-dialog.component';

@Injectable()
export class ActivityResolve implements Resolve<any> {
  constructor(private service: ActivityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id);
    }
    return new Activity();
  }
}

export const activityRoute: Routes = [
  {
    path: 'activity',
    component: ActivityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.activity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'activity/:id/view',
    component: ActivityDetailComponent,
    resolve: {
      activity: ActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.activity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'activity/new',
    component: ActivityUpdateComponent,
    resolve: {
      activity: ActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.activity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'activity/:id/edit',
    component: ActivityUpdateComponent,
    resolve: {
      activity: ActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.activity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const activityPopupRoute: Routes = [
  {
    path: 'activity/:id/delete',
    component: ActivityDeletePopupComponent,
    resolve: {
      activity: ActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.activity.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
