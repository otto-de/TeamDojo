import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DojoSharedModule } from 'app/shared';
import {
    LevelService,
    LevelComponent,
    LevelDetailComponent,
    LevelUpdateComponent,
    LevelDeletePopupComponent,
    LevelDeleteDialogComponent,
    levelRoute,
    levelPopupRoute,
    LevelResolve
} from './';

const ENTITY_STATES = [...levelRoute, ...levelPopupRoute];

@NgModule({
    imports: [DojoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LevelComponent, LevelDetailComponent, LevelUpdateComponent, LevelDeleteDialogComponent, LevelDeletePopupComponent],
    entryComponents: [LevelComponent, LevelUpdateComponent, LevelDeleteDialogComponent, LevelDeletePopupComponent],
    providers: [LevelService, LevelResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DojoLevelModule {}
