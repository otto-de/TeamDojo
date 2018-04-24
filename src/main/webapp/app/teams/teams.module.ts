import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TeamdojoSharedModule } from 'app/shared';
import { TEAMS_ROUTE, TeamsComponent } from './';
import { TeamsStatusComponent } from './teams-status.component';
import { TeamsAchievementsComponent } from './teams-achievements.component';
import { TeamsSkillsComponent } from './teams-skills.component';

@NgModule({
    imports: [TeamdojoSharedModule, RouterModule.forChild([TEAMS_ROUTE])],
    declarations: [TeamsComponent, TeamsStatusComponent, TeamsAchievementsComponent, TeamsSkillsComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamsModule {}
