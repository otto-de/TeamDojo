import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReport } from 'app/shared/model/report.model';
import { ReportService } from './report.service';

@Component({
    selector: 'jhi-report-delete-dialog',
    templateUrl: './report-delete-dialog.component.html'
})
export class ReportDeleteDialogComponent {
    report: IReport;

    constructor(private reportService: ReportService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reportService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reportListModification',
                content: 'Deleted an report'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-report-delete-popup',
    template: ''
})
export class ReportDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ report }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReportDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.report = report.body;
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
