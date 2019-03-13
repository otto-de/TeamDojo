import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';

@Component({
  selector: 'jhi-comment-update',
  templateUrl: './comment-update.component.html'
})
export class CommentUpdateComponent implements OnInit {
  private _comment: IComment;
  isSaving: boolean;

  teams: ITeam[];

  skills: ISkill[];
  creationDate: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private commentService: CommentService,
    private teamService: TeamService,
    private skillService: SkillService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.route.data.subscribe(({ comment }) => {
      this.comment = comment.body ? comment.body : comment;
    });
    this.teamService.query().subscribe(
      (res: HttpResponse<ITeam[]>) => {
        this.teams = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.skillService.query().subscribe(
      (res: HttpResponse<ISkill[]>) => {
        this.skills = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.comment.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
    if (this.comment.id !== undefined) {
      this.subscribeToSaveResponse(this.commentService.update(this.comment));
    } else {
      this.subscribeToSaveResponse(this.commentService.create(this.comment));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>) {
    result.subscribe((res: HttpResponse<IComment>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(result: IComment) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTeamById(index: number, item: ITeam) {
    return item.id;
  }

  trackSkillById(index: number, item: ISkill) {
    return item.id;
  }
  get comment() {
    return this._comment;
  }

  set comment(comment: IComment) {
    this._comment = comment;
    this.creationDate = moment(comment.creationDate).format();
  }
}
