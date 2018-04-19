import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DojoSharedModule } from 'app/shared';
import {
    TeamService,
    TeamComponent,
    TeamDetailComponent,
    TeamUpdateComponent,
    TeamDeletePopupComponent,
    TeamDeleteDialogComponent,
    teamRoute,
    teamPopupRoute,
    TeamResolve
} from './';

const ENTITY_STATES = [...teamRoute, ...teamPopupRoute];

@NgModule({
    imports: [DojoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TeamComponent, TeamDetailComponent, TeamUpdateComponent, TeamDeleteDialogComponent, TeamDeletePopupComponent],
    entryComponents: [TeamComponent, TeamUpdateComponent, TeamDeleteDialogComponent, TeamDeletePopupComponent],
    providers: [TeamService, TeamResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DojoTeamModule {}
