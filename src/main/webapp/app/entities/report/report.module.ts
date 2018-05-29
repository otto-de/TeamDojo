import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TeamdojoSharedModule } from 'app/shared';
import {
    ReportService,
    ReportComponent,
    ReportDetailComponent,
    ReportUpdateComponent,
    ReportDeletePopupComponent,
    ReportDeleteDialogComponent,
    reportRoute,
    reportPopupRoute,
    ReportResolve
} from './';

const ENTITY_STATES = [...reportRoute, ...reportPopupRoute];

@NgModule({
    imports: [TeamdojoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ReportComponent, ReportDetailComponent, ReportUpdateComponent, ReportDeleteDialogComponent, ReportDeletePopupComponent],
    entryComponents: [ReportComponent, ReportUpdateComponent, ReportDeleteDialogComponent, ReportDeletePopupComponent],
    providers: [ReportService, ReportResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamdojoReportModule {}
