import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DojoSharedModule } from 'app/shared';
import {
    BadgeSkillService,
    BadgeSkillComponent,
    BadgeSkillDetailComponent,
    BadgeSkillUpdateComponent,
    BadgeSkillDeletePopupComponent,
    BadgeSkillDeleteDialogComponent,
    badgeSkillRoute,
    badgeSkillPopupRoute,
    BadgeSkillResolve
} from './';

const ENTITY_STATES = [...badgeSkillRoute, ...badgeSkillPopupRoute];

@NgModule({
    imports: [DojoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BadgeSkillComponent,
        BadgeSkillDetailComponent,
        BadgeSkillUpdateComponent,
        BadgeSkillDeleteDialogComponent,
        BadgeSkillDeletePopupComponent
    ],
    entryComponents: [BadgeSkillComponent, BadgeSkillUpdateComponent, BadgeSkillDeleteDialogComponent, BadgeSkillDeletePopupComponent],
    providers: [BadgeSkillService, BadgeSkillResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DojoBadgeSkillModule {}
