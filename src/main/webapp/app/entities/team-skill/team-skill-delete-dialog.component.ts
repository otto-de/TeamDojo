import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { TeamSkillService } from './team-skill.service';

@Component({
  selector: 'jhi-team-skill-delete-dialog',
  templateUrl: './team-skill-delete-dialog.component.html'
})
export class TeamSkillDeleteDialogComponent {
  teamSkill: ITeamSkill;

  constructor(private teamSkillService: TeamSkillService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.teamSkillService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'teamSkillListModification',
        content: 'Deleted an teamSkill'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-team-skill-delete-popup',
  template: ''
})
export class TeamSkillDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.route.data.subscribe(({ teamSkill }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TeamSkillDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.teamSkill = teamSkill.body;
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
