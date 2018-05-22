import { NgModule } from '@angular/core';

import { FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, TeamdojoSharedLibsModule } from './';
import { ToggleSwitchComponent } from './toogleswitch/toggle-switch.component';
import { ImageDataUrlPipe } from './pipe/image-data-url.pipe';
import { TruncateStringPipe } from './pipe/truncate-string.pipe';
import { AchievementItemComponent } from 'app/shared/achievement';

@NgModule({
    imports: [TeamdojoSharedLibsModule],
    declarations: [
        FindLanguageFromKeyPipe,
        ToggleSwitchComponent,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        ImageDataUrlPipe,
        TruncateStringPipe,
        AchievementItemComponent
    ],
    providers: [],
    exports: [
        TeamdojoSharedLibsModule,
        FindLanguageFromKeyPipe,
        ToggleSwitchComponent,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        ImageDataUrlPipe,
        TruncateStringPipe,
        AchievementItemComponent
    ]
})
export class TeamdojoSharedCommonModule {}
