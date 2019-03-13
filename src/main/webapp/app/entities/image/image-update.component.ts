import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiDataUtils } from 'ng-jhipster';

import { IImage } from 'app/shared/model/image.model';
import { ImageService } from './image.service';

@Component({
  selector: 'jhi-image-update',
  templateUrl: './image-update.component.html'
})
export class ImageUpdateComponent implements OnInit {
  private _image: IImage;
  isSaving: boolean;

  constructor(
    private dataUtils: JhiDataUtils,
    private imageService: ImageService,
    private elementRef: ElementRef,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.route.data.subscribe(({ image }) => {
      this.image = image.body ? image.body : image;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, entity, field, isImage) {
    this.dataUtils.setFileData(event, entity, field, isImage);
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.dataUtils.clearInputImage(this.image, this.elementRef, field, fieldContentType, idInput);
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.image.id !== undefined) {
      this.subscribeToSaveResponse(this.imageService.update(this.image));
    } else {
      this.subscribeToSaveResponse(this.imageService.create(this.image));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IImage>>) {
    result.subscribe((res: HttpResponse<IImage>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(result: IImage) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get image() {
    return this._image;
  }

  set image(image: IImage) {
    this._image = image;
  }
}
