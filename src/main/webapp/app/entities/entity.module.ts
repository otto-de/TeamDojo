import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DojoDimensionModule } from './dimension/dimension.module';
import { DojoSkillModule } from './skill/skill.module';
import { DojoTeamModule } from './team/team.module';
import { DojoTeamSkillModule } from './team-skill/team-skill.module';
import { DojoLevelModule } from './level/level.module';
import { DojoBadgeModule } from './badge/badge.module';
import { DojoBadgeSkillModule } from './badge-skill/badge-skill.module';
import { DojoLevelSkillModule } from './level-skill/level-skill.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        DojoDimensionModule,
        DojoSkillModule,
        DojoTeamModule,
        DojoTeamSkillModule,
        DojoLevelModule,
        DojoBadgeModule,
        DojoBadgeSkillModule,
        DojoLevelSkillModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DojoEntityModule {}
