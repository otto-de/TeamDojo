import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from './organization.service';

@Component({
    selector: 'jhi-organization-delete-dialog',
    templateUrl: './organization-delete-dialog.component.html'
})
export class OrganizationDeleteDialogComponent {
    organization: IOrganization;

    constructor(
        private organizationService: OrganizationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.organizationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'organizationListModification',
                content: 'Deleted an organization'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-organization-delete-popup',
    template: ''
})
export class OrganizationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ organization }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OrganizationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.organization = organization.body;
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
