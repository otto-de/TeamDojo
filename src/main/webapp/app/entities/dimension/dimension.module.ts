import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TeamdojoSharedModule } from 'app/shared';
import {
    DimensionService,
    DimensionComponent,
    DimensionDetailComponent,
    DimensionUpdateComponent,
    DimensionDeletePopupComponent,
    DimensionDeleteDialogComponent,
    dimensionRoute,
    dimensionPopupRoute,
    DimensionResolve
} from './';

const ENTITY_STATES = [...dimensionRoute, ...dimensionPopupRoute];

@NgModule({
    imports: [TeamdojoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DimensionComponent,
        DimensionDetailComponent,
        DimensionUpdateComponent,
        DimensionDeleteDialogComponent,
        DimensionDeletePopupComponent
    ],
    entryComponents: [DimensionComponent, DimensionUpdateComponent, DimensionDeleteDialogComponent, DimensionDeletePopupComponent],
    providers: [DimensionService, DimensionResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamdojoDimensionModule {}
