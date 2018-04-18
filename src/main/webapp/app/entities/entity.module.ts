import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DojoDimensionModule } from './dimension/dimension.module';
import { DojoSkillModule } from './skill/skill.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        DojoDimensionModule,
        DojoSkillModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DojoEntityModule {}
