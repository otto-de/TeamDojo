import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { TeamdojoSharedModule } from 'app/shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    adminState,
    AuditResolvePagingParams,
    AuditsComponent,
    UserMgmtComponent,
    UserMgmtDetailComponent,
    UserMgmtUpdateComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    JhiMetricsMonitoringComponent,
    JhiHealthModalComponent,
    JhiHealthCheckComponent,
    JhiConfigurationComponent,
    JhiDocsComponent,
    AuditsService,
    JhiConfigurationService,
    JhiHealthService,
    JhiMetricsService,
    JhiTrackerComponent,
    LogsService,
    UserResolvePagingParams,
    UserMgmtResolve,
    UserResolve
} from './';

@NgModule({
    imports: [
        TeamdojoSharedModule,
        RouterModule.forChild(adminState)
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserMgmtDetailComponent,
        UserMgmtUpdateComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        JhiConfigurationComponent,
        JhiHealthCheckComponent,
        JhiHealthModalComponent,
        JhiDocsComponent,
        JhiTrackerComponent,
        JhiMetricsMonitoringComponent
    ],
    entryComponents: [UserMgmtUpdateComponent, UserMgmtDeleteDialogComponent, JhiHealthModalComponent],
    providers: [
        AuditResolvePagingParams,
        AuditsService,
        JhiConfigurationService,
        JhiHealthService,
        JhiMetricsService,
        LogsService,
        UserResolvePagingParams,
        UserResolve,
        UserMgmtResolve
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamdojoAdminModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => this.languageService.changeLanguage(languageKey));
    }
}
