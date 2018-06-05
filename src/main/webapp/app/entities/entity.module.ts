import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TeamdojoDimensionModule } from './dimension/dimension.module';
import { TeamdojoSkillModule } from './skill/skill.module';
import { TeamdojoTeamModule } from './team/team.module';
import { TeamdojoTeamSkillModule } from './team-skill/team-skill.module';
import { TeamdojoLevelModule } from './level/level.module';
import { TeamdojoBadgeModule } from './badge/badge.module';
import { TeamdojoBadgeSkillModule } from './badge-skill/badge-skill.module';
import { TeamdojoLevelSkillModule } from './level-skill/level-skill.module';
import { TeamdojoOrganizationModule } from './organization/organization.module';
import { TeamdojoReportModule } from './report/report.module';
import { TeamdojoCommentModule } from './comment/comment.module';
import { TeamdojoActivityModule } from './activity/activity.module';
import { TeamdojoImageModule } from './image/image.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        TeamdojoDimensionModule,
        TeamdojoSkillModule,
        TeamdojoTeamModule,
        TeamdojoTeamSkillModule,
        TeamdojoLevelModule,
        TeamdojoBadgeModule,
        TeamdojoBadgeSkillModule,
        TeamdojoLevelSkillModule,
        TeamdojoOrganizationModule,
        TeamdojoReportModule,
        TeamdojoCommentModule,
        TeamdojoActivityModule,
        TeamdojoImageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamdojoEntityModule {}
