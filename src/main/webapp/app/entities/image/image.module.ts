import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TeamdojoSharedModule } from 'app/shared';
import {
  ImageService,
  ImageComponent,
  ImageDetailComponent,
  ImageUpdateComponent,
  ImageDeletePopupComponent,
  ImageDeleteDialogComponent,
  imageRoute,
  imagePopupRoute,
  ImageResolve
} from './';

const ENTITY_STATES = [...imageRoute, ...imagePopupRoute];

@NgModule({
  imports: [TeamdojoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ImageComponent, ImageDetailComponent, ImageUpdateComponent, ImageDeleteDialogComponent, ImageDeletePopupComponent],
  entryComponents: [ImageComponent, ImageUpdateComponent, ImageDeleteDialogComponent, ImageDeletePopupComponent],
  providers: [ImageService, ImageResolve],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TeamdojoImageModule {}
