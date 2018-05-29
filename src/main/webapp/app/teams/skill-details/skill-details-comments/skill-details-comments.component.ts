import { Component, Input, OnInit } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';
import { TeamsSelectionService } from 'app/teams/teams-selection/teams-selection.service';
import { Comment, IComment } from 'app/shared/model/comment.model';
import { HttpResponse } from '@angular/common/http';
import * as moment from 'moment';
import { CommentService } from 'app/entities/comment';
import { IAchievableSkill } from 'app/shared/model/achievable-skill.model';

@Component({
    selector: 'jhi-skill-details-comments',
    templateUrl: './skill-details-comments.component.html',
    styleUrls: ['./skill-details-comments.scss']
})
export class SkillDetailsCommentsComponent implements OnInit {
    @Input() team: ITeam;
    @Input() achievableSkill: IAchievableSkill;
    @Input() comments: IComment[];
    newComment: IComment;

    constructor(private commentService: CommentService, private teamsSelectionService: TeamsSelectionService) {}

    ngOnInit() {
        this.newComment = new Comment();
    }

    isActiveTeam() {
        return this.teamsSelectionService.selectedTeam && this.team && this.teamsSelectionService.selectedTeam.id === this.team.id;
    }

    onSubmit() {
        this.newComment.creationDate = moment();
        this.newComment.skillId = this.achievableSkill ? this.achievableSkill.skillId : undefined;
        this.newComment.skillTitle = this.achievableSkill ? this.achievableSkill.title : undefined;
        this.newComment.teamId = this.team ? this.team.id : undefined;
        this.newComment.teamShortName = this.team ? this.team.shortName : undefined;
        this.commentService.create(this.newComment).subscribe((res: HttpResponse<IComment>) => {
            if (res.body) {
                this.comments.push(res.body);
                this.newComment = new Comment();
            }
        });
    }
}
