import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { LevelSkillService } from './level-skill.service';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from 'app/entities/level';

@Component({
  selector: 'jhi-level-skill-update',
  templateUrl: './level-skill-update.component.html'
})
export class LevelSkillUpdateComponent implements OnInit {
  private _levelSkill: ILevelSkill;
  isSaving: boolean;

  skills: ISkill[];

  levels: ILevel[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private levelSkillService: LevelSkillService,
    private skillService: SkillService,
    private levelService: LevelService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.route.data.subscribe(({ levelSkill }) => {
      this.levelSkill = levelSkill.body ? levelSkill.body : levelSkill;
    });
    this.skillService.query().subscribe(
      (res: HttpResponse<ISkill[]>) => {
        this.skills = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.levelService.query().subscribe(
      (res: HttpResponse<ILevel[]>) => {
        this.levels = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.levelSkill.id !== undefined) {
      this.subscribeToSaveResponse(this.levelSkillService.update(this.levelSkill));
    } else {
      this.subscribeToSaveResponse(this.levelSkillService.create(this.levelSkill));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ILevelSkill>>) {
    result.subscribe((res: HttpResponse<ILevelSkill>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(result: ILevelSkill) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSkillById(index: number, item: ISkill) {
    return item.id;
  }

  trackLevelById(index: number, item: ILevel) {
    return item.id;
  }
  get levelSkill() {
    return this._levelSkill;
  }

  set levelSkill(levelSkill: ILevelSkill) {
    this._levelSkill = levelSkill;
  }
}
