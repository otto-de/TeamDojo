import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { LevelSkillService } from './level-skill.service';

@Component({
    selector: 'jhi-level-skill-delete-dialog',
    templateUrl: './level-skill-delete-dialog.component.html'
})
export class LevelSkillDeleteDialogComponent {
    levelSkill: ILevelSkill;

    constructor(private levelSkillService: LevelSkillService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.levelSkillService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'levelSkillListModification',
                content: 'Deleted an levelSkill'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-level-skill-delete-popup',
    template: ''
})
export class LevelSkillDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ levelSkill }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LevelSkillDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.levelSkill = levelSkill.body;
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
