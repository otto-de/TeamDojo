import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from './level.service';
import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from 'app/entities/dimension';

@Component({
    selector: 'jhi-level-update',
    templateUrl: './level-update.component.html'
})
export class LevelUpdateComponent implements OnInit {
    private _level: ILevel;
    isSaving: boolean;

    dimensions: IDimension[];

    dependsons: ILevel[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private levelService: LevelService,
        private dimensionService: DimensionService,
        private elementRef: ElementRef,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ level }) => {
            this.level = level.body ? level.body : level;
        });
        this.dimensionService.query().subscribe(
            (res: HttpResponse<IDimension[]>) => {
                this.dimensions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.levelService.query({ filter: 'level-is-null' }).subscribe(
            (res: HttpResponse<ILevel[]>) => {
                if (!this.level.dependsOn || !this.level.dependsOn.id) {
                    this.dependsons = res.body;
                } else {
                    this.levelService.find(this.level.dependsOn.id).subscribe(
                        (subRes: HttpResponse<ILevel>) => {
                            this.dependsons = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.dataUtils.clearInputImage(this.level, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.level.id !== undefined) {
            this.subscribeToSaveResponse(this.levelService.update(this.level));
        } else {
            this.subscribeToSaveResponse(this.levelService.create(this.level));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILevel>>) {
        result.subscribe((res: HttpResponse<ILevel>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ILevel) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDimensionById(index: number, item: IDimension) {
        return item.id;
    }

    trackLevelById(index: number, item: ILevel) {
        return item.id;
    }
    get level() {
        return this._level;
    }

    set level(level: ILevel) {
        this._level = level;
    }
}
