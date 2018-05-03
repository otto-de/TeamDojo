import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { TeamsSelectionComponent } from 'app/teams/teams-selection/teams-selection.component';

@Injectable()
export class TeamsSelectionService {
    private isOpen = false;

    private teamId = -1;

    constructor(private modalService: NgbModal) {}

    setTeamId(teamId: number) {
        this.teamId = teamId;
    }

    openModal(): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;
        const modalRef = this.modalService.open(TeamsSelectionComponent, { size: 'lg' });
        modalRef.result.then(
            result => {
                this.isOpen = false;
            },
            reason => {
                this.isOpen = false;
            }
        );
        return modalRef;
    }
}
