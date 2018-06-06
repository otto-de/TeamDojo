import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from './level.service';
import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from 'app/entities/dimension';
import { IImage } from 'app/shared/model/image.model';
import { ImageService } from 'app/entities/image';

@Component({
    selector: 'jhi-level-update',
    templateUrl: './level-update.component.html'
})
export class LevelUpdateComponent implements OnInit {
    private _level: ILevel;
    isSaving: boolean;

    dimensions: IDimension[];

    dependsons: ILevel[];

    images: IImage[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private levelService: LevelService,
        private dimensionService: DimensionService,
        private imageService: ImageService,
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
                if (!this.level.dependsOnId) {
                    this.dependsons = res.body;
                } else {
                    this.levelService.find(this.level.dependsOnId).subscribe(
                        (subRes: HttpResponse<ILevel>) => {
                            this.dependsons = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.imageService.query({ filter: 'level-is-null' }).subscribe(
            (res: HttpResponse<IImage[]>) => {
                if (!this.level.imageId) {
                    this.images = res.body;
                } else {
                    this.imageService.find(this.level.imageId).subscribe(
                        (subRes: HttpResponse<IImage>) => {
                            this.images = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    trackImageById(index: number, item: IImage) {
        return item.id;
    }
    get level() {
        return this._level;
    }

    set level(level: ILevel) {
        this._level = level;
    }
}
