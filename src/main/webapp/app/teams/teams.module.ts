import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TeamdojoSharedModule } from 'app/shared';
import { TEAMS_ROUTES, TeamsComponent } from './';
import { TeamsAchievementsComponent } from './teams-achievements.component';
import { TeamsSkillsComponent } from './teams-skills.component';
import { TeamsSelectionComponent } from 'app/shared/teams-selection/teams-selection.component';
import { TeamAndTeamSkillResolve } from './teams.route';
import { TeamsService } from './teams.service';
import { TeamsSkillsService } from './teams-skills.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TeamsAchievementsService } from 'app/teams/teams-achievements.service';
import { SkillDetailsComponent } from 'app/teams/skill-details/skill-details.component';
import { SkillDetailsInfoComponent } from 'app/shared/skill-details/skill-details-info.component';
import { SkillDetailsCommentsComponent } from 'app/shared/skill-details/skill-details-comments.component';
import { SkillDetailsRatingComponent } from 'app/teams/skill-details/skill-details-rating/skill-details-rating.component';
import { AllCommentsResolve, AllSkillsResolve, DojoModelResolve, SkillResolve } from 'app/shared/common.resolver';

@NgModule({
    imports: [TeamdojoSharedModule, RouterModule.forChild(TEAMS_ROUTES), NgbModule],
    declarations: [
        TeamsComponent,
        TeamsAchievementsComponent,
        TeamsSkillsComponent,
        TeamsSelectionComponent,
        SkillDetailsComponent,
        SkillDetailsInfoComponent,
        SkillDetailsCommentsComponent,
        SkillDetailsRatingComponent
    ],
    entryComponents: [TeamsSelectionComponent],
    providers: [
        DojoModelResolve,
        TeamsService,
        TeamsSkillsService,
        TeamsAchievementsService,
        TeamAndTeamSkillResolve,
        SkillResolve,
        AllSkillsResolve,
        AllCommentsResolve
    ],
    exports: [SkillDetailsInfoComponent, SkillDetailsCommentsComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamsModule {}
