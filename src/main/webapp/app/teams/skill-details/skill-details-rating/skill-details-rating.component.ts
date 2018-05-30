import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ISkillRate, SkillRate } from 'app/shared/model/skill-rate.model';
import { Comment, IComment } from 'app/shared/model/comment.model';
import { HttpResponse } from '@angular/common/http';
import { ITeam } from 'app/shared/model/team.model';
import * as moment from 'moment';
import { CommentService } from 'app/entities/comment';
import { TeamsSelectionService } from 'app/teams/teams-selection/teams-selection.service';

@Component({
    selector: 'jhi-star-rating',
    templateUrl: './skill-details-rating.component.html',
    styleUrls: ['./skill-details-rating.scss']
})
export class SkillDetailsRatingComponent implements OnInit {
    @Input() skill: ISkill;
    @Output() onVoteSubmitted = new EventEmitter<{ skillRate: ISkillRate; comment: IComment }>();

    private rateScore;
    private comment: string;
    private modalRef;
    private newComment: IComment;

    constructor(
        private skillService: SkillService,
        private modalService: NgbModal,
        private commentService: CommentService,
        private teamsSelectionService: TeamsSelectionService
    ) {}

    ngOnInit(): void {
        this.rateScore = this.skill.rateScore;
        this.newComment = new Comment();
    }

    isActiveTeam(): Boolean {
        return this.teamsSelectionService.selectedTeam !== null && typeof this.teamsSelectionService.selectedTeam !== 'undefined';
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

        this.newComment.text = '[RATING] - ' + this.comment;
        this.submitComment();
        this.comment = '';
        this.modalRef.close();
    }

    submitComment() {
        const team = this.teamsSelectionService.selectedTeam;
        this.newComment.creationDate = moment();
        this.newComment.skillId = this.skill ? this.skill.id : undefined;
        this.newComment.skillTitle = this.skill ? this.skill.title : undefined;
        this.newComment.teamId = team ? team.id : undefined;
        this.newComment.teamShortName = team ? team.shortName : undefined;
        this.commentService.create(this.newComment).subscribe((res: HttpResponse<IComment>) => {
            if (res.body) {
                this.newComment = new Comment();
                this.onVoteSubmitted.emit({ skillRate: null, comment: res.body });
            }
        });
    }

    open(content) {
        if (this.isActiveTeam()) {
            this.modalRef = this.modalService.open(content);
        }
    }
}
