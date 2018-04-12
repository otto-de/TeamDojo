import { NgModule } from '@angular/core';

import { DojoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [DojoSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    providers: [],
    exports: [DojoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class DojoSharedCommonModule {}
