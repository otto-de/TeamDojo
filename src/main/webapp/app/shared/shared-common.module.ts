import { NgModule } from '@angular/core';

import { FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, TeamdojoSharedLibsModule } from './';
import { ToggleSwitchComponent } from './toogleswitch/toggle-switch.component';
import { ImageDataUrlPipe } from './pipe/image-data-url.pipe';
import { TruncateStringPipe } from './pipe/truncate-string.pipe';

@NgModule({
    imports: [TeamdojoSharedLibsModule],
    declarations: [
        FindLanguageFromKeyPipe,
        ToggleSwitchComponent,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        ImageDataUrlPipe,
        TruncateStringPipe
    ],
    providers: [],
    exports: [
        TeamdojoSharedLibsModule,
        FindLanguageFromKeyPipe,
        ToggleSwitchComponent,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        ImageDataUrlPipe,
        TruncateStringPipe
    ]
})
export class TeamdojoSharedCommonModule {}
