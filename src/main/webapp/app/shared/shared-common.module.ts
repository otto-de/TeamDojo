import { NgModule } from '@angular/core';

import { FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, TeamdojoSharedLibsModule } from './';
import { ToggleSwitchComponent } from './toogleswitch/toggle-switch.component';

@NgModule({
    imports: [TeamdojoSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, ToggleSwitchComponent, JhiAlertComponent, JhiAlertErrorComponent],
    providers: [],
    exports: [TeamdojoSharedLibsModule, FindLanguageFromKeyPipe, ToggleSwitchComponent, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TeamdojoSharedCommonModule {}
