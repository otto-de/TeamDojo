import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TeamdojoSharedModule } from 'app/shared';
import {
    TeamSkillService,
    TeamSkillComponent,
    TeamSkillDetailComponent,
    TeamSkillUpdateComponent,
    TeamSkillDeletePopupComponent,
    TeamSkillDeleteDialogComponent,
    teamSkillRoute,
    teamSkillPopupRoute,
    TeamSkillResolve
} from './';

const ENTITY_STATES = [...teamSkillRoute, ...teamSkillPopupRoute];

@NgModule({
    imports: [TeamdojoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TeamSkillComponent,
        TeamSkillDetailComponent,
        TeamSkillUpdateComponent,
        TeamSkillDeleteDialogComponent,
        TeamSkillDeletePopupComponent
    ],
    entryComponents: [TeamSkillComponent, TeamSkillUpdateComponent, TeamSkillDeleteDialogComponent, TeamSkillDeletePopupComponent],
    providers: [TeamSkillService, TeamSkillResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamdojoTeamSkillModule {}
