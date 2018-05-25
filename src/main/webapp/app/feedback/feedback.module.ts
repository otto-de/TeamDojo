import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TeamdojoSharedModule } from 'app/shared/index';
import { FeedbackService } from './feedback.service';
import { FeedbackComponent } from './feedback.component';
import { feedbackRoute } from './feedback.route';

const ENTITY_STATES = [...feedbackRoute];

@NgModule({
    imports: [TeamdojoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FeedbackComponent],
    entryComponents: [FeedbackComponent],
    providers: [FeedbackService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FeedbackModule {}
