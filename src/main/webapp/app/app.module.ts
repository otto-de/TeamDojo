import './vendor.ts';

import { Injector, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';
import { StarRatingModule } from 'angular-star-rating';

import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { TeamdojoSharedModule } from 'app/shared';
import { TeamdojoCoreModule } from 'app/core';
import { TeamdojoAppRoutingModule } from './app-routing.module';
import { TeamdojoAccountModule } from './account/account.module';
import { TeamdojoEntityModule } from './entities/entity.module';
import { OverviewModule } from 'app/overview';
import { TeamsModule } from './teams/teams.module';
import { FeedbackModule } from './feedback/feedback.module';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { StateStorageService } from 'app/core/auth/state-storage.service';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    ActiveMenuDirective,
    ErrorComponent,
    FooterComponent,
    JhiMainComponent,
    NavbarComponent,
    PageRibbonComponent,
    ProfileService
} from './layouts';

@NgModule({
    imports: [
        StarRatingModule.forRoot(),
        BrowserModule,
        TeamdojoAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        TeamdojoSharedModule,
        TeamdojoCoreModule,
        OverviewModule,
        TeamdojoAccountModule,
        TeamdojoEntityModule,
        TeamsModule,
        FeedbackModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    providers: [
        ProfileService,
        PaginationConfig,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [StateStorageService, Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class TeamdojoAppModule {}
