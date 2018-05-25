import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { HasAnyAuthorityDirective, JhiLoginModalComponent, TeamdojoSharedCommonModule, TeamdojoSharedLibsModule } from './';
import { BackgroundComponent } from 'app/shared/background/background.component';

@NgModule({
    imports: [TeamdojoSharedLibsModule, TeamdojoSharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, BackgroundComponent],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [TeamdojoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, BackgroundComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamdojoSharedModule {}
