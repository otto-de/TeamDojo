import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TeamdojoSharedModule } from 'app/shared';
import {
  LevelSkillService,
  LevelSkillComponent,
  LevelSkillDetailComponent,
  LevelSkillUpdateComponent,
  LevelSkillDeletePopupComponent,
  LevelSkillDeleteDialogComponent,
  levelSkillRoute,
  levelSkillPopupRoute,
  LevelSkillResolve
} from './';

const ENTITY_STATES = [...levelSkillRoute, ...levelSkillPopupRoute];

@NgModule({
  imports: [TeamdojoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LevelSkillComponent,
    LevelSkillDetailComponent,
    LevelSkillUpdateComponent,
    LevelSkillDeleteDialogComponent,
    LevelSkillDeletePopupComponent
  ],
  entryComponents: [LevelSkillComponent, LevelSkillUpdateComponent, LevelSkillDeleteDialogComponent, LevelSkillDeletePopupComponent],
  providers: [LevelSkillService, LevelSkillResolve],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamdojoLevelSkillModule {}
