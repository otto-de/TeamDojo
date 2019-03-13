import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Report } from 'app/shared/model/report.model';
import { ReportService } from './report.service';
import { ReportComponent } from './report.component';
import { ReportDetailComponent } from './report-detail.component';
import { ReportUpdateComponent } from './report-update.component';
import { ReportDeletePopupComponent } from './report-delete-dialog.component';

@Injectable()
export class ReportResolve implements Resolve<any> {
  constructor(private service: ReportService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id);
    }
    return new Report();
  }
}

export const reportRoute: Routes = [
  {
    path: 'report',
    component: ReportComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.report.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'report/:id/view',
    component: ReportDetailComponent,
    resolve: {
      report: ReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.report.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'report/new',
    component: ReportUpdateComponent,
    resolve: {
      report: ReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.report.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'report/:id/edit',
    component: ReportUpdateComponent,
    resolve: {
      report: ReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.report.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const reportPopupRoute: Routes = [
  {
    path: 'report/:id/delete',
    component: ReportDeletePopupComponent,
    resolve: {
      report: ReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.report.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
