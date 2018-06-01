import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TeamdojoSharedModule } from 'app/shared';
import {
    ActivityService,
    ActivityComponent,
    ActivityDetailComponent,
    ActivityUpdateComponent,
    ActivityDeletePopupComponent,
    ActivityDeleteDialogComponent,
    activityRoute,
    activityPopupRoute,
    ActivityResolve
} from './';

const ENTITY_STATES = [...activityRoute, ...activityPopupRoute];

@NgModule({
    imports: [TeamdojoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActivityComponent,
        ActivityDetailComponent,
        ActivityUpdateComponent,
        ActivityDeleteDialogComponent,
        ActivityDeletePopupComponent
    ],
    entryComponents: [ActivityComponent, ActivityUpdateComponent, ActivityDeleteDialogComponent, ActivityDeletePopupComponent],
    providers: [ActivityService, ActivityResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamdojoActivityModule {}
