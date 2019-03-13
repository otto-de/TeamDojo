import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Organization } from 'app/shared/model/organization.model';
import { OrganizationService } from './organization.service';
import { OrganizationComponent } from './organization.component';
import { OrganizationDetailComponent } from './organization-detail.component';
import { OrganizationUpdateComponent } from './organization-update.component';
import { OrganizationDeletePopupComponent } from './organization-delete-dialog.component';

@Injectable()
export class OrganizationResolve implements Resolve<any> {
  constructor(private service: OrganizationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id);
    }
    return new Organization();
  }
}

export const organizationRoute: Routes = [
  {
    path: 'organization',
    component: OrganizationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.organization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'organization/:id/view',
    component: OrganizationDetailComponent,
    resolve: {
      organization: OrganizationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.organization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'organization/new',
    component: OrganizationUpdateComponent,
    resolve: {
      organization: OrganizationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.organization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'organization/:id/edit',
    component: OrganizationUpdateComponent,
    resolve: {
      organization: OrganizationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.organization.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const organizationPopupRoute: Routes = [
  {
    path: 'organization/:id/delete',
    component: OrganizationDeletePopupComponent,
    resolve: {
      organization: OrganizationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.organization.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
