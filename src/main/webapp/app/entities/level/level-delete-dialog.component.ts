import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from './level.service';

@Component({
    selector: 'jhi-level-delete-dialog',
    templateUrl: './level-delete-dialog.component.html'
})
export class LevelDeleteDialogComponent {
    level: ILevel;

    constructor(private levelService: LevelService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.levelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'levelListModification',
                content: 'Deleted an level'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-level-delete-popup',
    template: ''
})
export class LevelDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ level }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LevelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.level = level.body;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
