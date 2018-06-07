import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IImage } from 'app/shared/model/image.model';

@Component({
    selector: 'jhi-image-detail',
    templateUrl: './image-detail.component.html'
})
export class ImageDetailComponent implements OnInit {
    image: IImage;

    constructor(private dataUtils: JhiDataUtils, private route: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
