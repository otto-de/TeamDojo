import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DojoDimensionModule } from './dimension/dimension.module';
import { DojoSkillModule } from './skill/skill.module';
import { DojoTeamModule } from './team/team.module';
import { DojoTeamSkillModule } from './team-skill/team-skill.module';
import { DojoBadgeModule } from './badge/badge.module';
import { DojoBadgeSkillModule } from './badge-skill/badge-skill.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        DojoDimensionModule,
        DojoSkillModule,
        DojoTeamModule,
        DojoTeamSkillModule,
        DojoBadgeModule,
        DojoBadgeSkillModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DojoEntityModule {}
