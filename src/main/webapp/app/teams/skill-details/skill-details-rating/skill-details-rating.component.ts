import { Component, Input, OnInit } from '@angular/core';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SkillRate } from 'app/shared/model/skill-rate.model';
import { IComment } from 'app/shared/model/comment.model';
import { HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-star-rating',
    templateUrl: './skill-details-rating.component.html',
    styleUrls: ['./skill-details-rating.scss']
})
export class SkillDetailsRatingComponent implements OnInit {
    @Input() skill: ISkill;
    private rateScore;
    private comment: string;
    private modalRef;

    constructor(private skillService: SkillService, private modalService: NgbModal) {}

    ngOnInit(): void {
        this.rateScore = this.skill.rateScore;
    }

    voteSkill(content: any) {
        console.log('in voteSkill');
        const rate = new SkillRate(this.skill.id, this.rateScore);
        this.skillService.createVote(rate).subscribe((res: HttpResponse<ISkill>) => {
            if (res.body) {
                this.skill = res.body;
                this.rateScore = this.skill.rateScore;
            }
        });

        this.modalRef.close();
        this.comment = '';
    }

    open(content) {
        this.modalRef = this.modalService.open(content);
    }
}
