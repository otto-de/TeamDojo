import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TeamdojoSharedModule } from 'app/shared';
import { SkillResolve, TEAMS_ROUTES, TeamsComponent } from './';
import { TeamsStatusComponent } from './teams-status.component';
import { TeamsAchievementsComponent } from './teams-achievements.component';
import { TeamsSkillsComponent } from './teams-skills.component';
import { TeamsSelectionComponent } from 'app/teams/teams-selection/teams-selection.component';
import { AllBadgeSkillsResolve, AllBadgesResolve, AllLevelSkillsResolve, AllLevelsResolve, TeamAndTeamSkillResolve } from './teams.route';
import { TeamsService } from './teams.service';
import { TeamsSkillsService } from './teams-skills.service';
import { TeamsSelectionService } from './teams-selection/teams-selection.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TeamsAchievementsService } from 'app/teams/teams-achievements.service';
import { SkillDetailsComponent } from 'app/teams/skill-details/skill-details.component';
import { SkillDetailsInfoComponent } from 'app/teams/skill-details/skill-details-info/skill-details-info.component';

@NgModule({
    imports: [TeamdojoSharedModule, RouterModule.forChild(TEAMS_ROUTES), NgbModule],
    declarations: [
        TeamsComponent,
        TeamsStatusComponent,
        TeamsAchievementsComponent,
        TeamsSkillsComponent,
        TeamsSelectionComponent,
        SkillDetailsComponent,
        SkillDetailsInfoComponent
    ],
    entryComponents: [TeamsSelectionComponent],
    providers: [
        TeamsService,
        TeamsSkillsService,
        TeamsSelectionService,
        TeamsAchievementsService,
        TeamAndTeamSkillResolve,
        SkillResolve,
        AllLevelsResolve,
        AllBadgesResolve,
        AllLevelSkillsResolve,
        AllBadgeSkillsResolve
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamsModule {}
