import { NgModule } from '@angular/core';

import { FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, TeamdojoSharedLibsModule } from './';
import { ImageDataUrlPipe } from './pipe/image-data-url.pipe';
import { TruncateStringPipe } from './pipe/truncate-string.pipe';
import { AchievementItemComponent } from 'app/shared/achievement';
import { NotificationComponent } from 'app/shared/notification/notification.component';

@NgModule({
    imports: [TeamdojoSharedLibsModule],
    declarations: [
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        ImageDataUrlPipe,
        TruncateStringPipe,
        AchievementItemComponent,
        NotificationComponent
    ],
    providers: [],
    exports: [
        TeamdojoSharedLibsModule,
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        ImageDataUrlPipe,
        TruncateStringPipe,
        AchievementItemComponent,
        NotificationComponent
    ]
})
export class TeamdojoSharedCommonModule {}
