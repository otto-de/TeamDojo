import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TeamdojoSharedModule } from 'app/shared';
import { OVERVIEW_ROUTE } from 'app/overview/overview.route';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { OverviewComponent } from 'app/overview/overview.component';

@NgModule({
    imports: [TeamdojoSharedModule, RouterModule.forChild([OVERVIEW_ROUTE]), NgbModule],
    declarations: [OverviewComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OverviewModule {}
