import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DojoSharedModule } from 'app/shared';
import {
    SkillService,
    SkillComponent,
    SkillDetailComponent,
    SkillUpdateComponent,
    SkillDeletePopupComponent,
    SkillDeleteDialogComponent,
    skillRoute,
    skillPopupRoute,
    SkillResolve
} from './';

const ENTITY_STATES = [...skillRoute, ...skillPopupRoute];

@NgModule({
    imports: [DojoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SkillComponent, SkillDetailComponent, SkillUpdateComponent, SkillDeleteDialogComponent, SkillDeletePopupComponent],
    entryComponents: [SkillComponent, SkillUpdateComponent, SkillDeleteDialogComponent, SkillDeletePopupComponent],
    providers: [SkillService, SkillResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DojoSkillModule {}
