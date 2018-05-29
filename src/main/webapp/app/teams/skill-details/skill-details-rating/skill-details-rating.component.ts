import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { ISkill } from 'app/shared/model/skill.model';
import { ITeam } from 'app/shared/model/team.model';
import { SkillService } from 'app/entities/skill';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-star-rating',
    templateUrl: './skill-details-rating.component.html',
    styleUrls: ['./skill-details-rating.scss']
})
export class SkillDetailsRatingComponent implements OnInit {
    @Input() skill: ISkill;
    private rateScore = {};
    private showModal = false;
    closeResult: string;

    constructor(private skillService: SkillService, private modalService: NgbModal) {
        this.rateScore = 0;
    }

    ngOnInit(): void {
        this.rateScore = this.skill.rateScore;
    }

    modalShow() {
        this.showModal = true;
        console.log(this.showModal);
    }

    modalHide() {
        this.showModal = false;
    }

    voteSkill(event: any) {
        console.log('clicked');
        this.modalShow();
        console.log('VOTE: ', this.showModal);
    }

    open(content) {
        this.modalService.open(content);
        /*this.modalService.open(content).result.then((result) => {
            this.closeResult = `Closed with: ${result}`;
        }, (reason) => {
            this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        });*/
    }
    /*private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return  `with: ${reason}`;
        }
    }*/
}
