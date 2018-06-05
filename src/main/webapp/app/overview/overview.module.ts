import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TeamdojoSharedModule } from 'app/shared';
import {
    AllBadgeSkillsResolve,
    AllBadgesResolve,
    AllCommentsResolve,
    AllLevelSkillsResolve,
    AllLevelsResolve,
    AllSkillsResolve,
    AllTeamSkillsResolve,
    AllTeamsResolve,
    OVERVIEW_ROUTE,
    OverviewResolve,
    SkillResolve
} from 'app/overview/overview.route';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { OverviewComponent } from 'app/overview/overview.component';
import { OverviewTeamsComponent } from 'app/overview/teams/overview-teams.component';
import { OverviewAchievementsComponent } from 'app/overview/achievements/overview-achievements.component';
import { OverviewSkillsComponent } from 'app/overview/skills/overview-skills.component';
import { OverviewSkillDetailsComponent } from 'app/overview/skills/skill-details/overview-skill-details.component';
import { TeamsModule } from 'app/teams';
import { BreadcrumbService } from 'app/layouts/navbar/breadcrumb.service';

@NgModule({
    imports: [TeamdojoSharedModule, RouterModule.forChild(OVERVIEW_ROUTE), NgbModule, TeamsModule],
    declarations: [
        OverviewComponent,
        OverviewTeamsComponent,
        OverviewAchievementsComponent,
        OverviewSkillsComponent,
        OverviewSkillDetailsComponent
    ],
    entryComponents: [],
    providers: [
        OverviewResolve,
        AllTeamsResolve,
        AllLevelsResolve,
        AllBadgesResolve,
        AllTeamSkillsResolve,
        AllLevelSkillsResolve,
        SkillResolve,
        AllBadgeSkillsResolve,
        AllSkillsResolve,
        AllCommentsResolve,
        BreadcrumbService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OverviewModule {}
