import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { HasAnyAuthorityDirective, JhiLoginModalComponent, TeamdojoSharedCommonModule, TeamdojoSharedLibsModule } from './';

@NgModule({
    imports: [TeamdojoSharedLibsModule, TeamdojoSharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [TeamdojoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamdojoSharedModule {}
