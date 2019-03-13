import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from './dimension.service';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';
import { IBadge } from 'app/shared/model/badge.model';
import { BadgeService } from 'app/entities/badge';

@Component({
  selector: 'jhi-dimension-update',
  templateUrl: './dimension-update.component.html'
})
export class DimensionUpdateComponent implements OnInit {
  private _dimension: IDimension;
  isSaving: boolean;

  teams: ITeam[];

  badges: IBadge[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private dimensionService: DimensionService,
    private teamService: TeamService,
    private badgeService: BadgeService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.route.data.subscribe(({ dimension }) => {
      this.dimension = dimension.body ? dimension.body : dimension;
    });
    this.teamService.query().subscribe(
      (res: HttpResponse<ITeam[]>) => {
        this.teams = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.badgeService.query().subscribe(
      (res: HttpResponse<IBadge[]>) => {
        this.badges = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.dimension.id !== undefined) {
      this.subscribeToSaveResponse(this.dimensionService.update(this.dimension));
    } else {
      this.subscribeToSaveResponse(this.dimensionService.create(this.dimension));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IDimension>>) {
    result.subscribe((res: HttpResponse<IDimension>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(result: IDimension) {
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

  trackBadgeById(index: number, item: IBadge) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
  get dimension() {
    return this._dimension;
  }

  set dimension(dimension: IDimension) {
    this._dimension = dimension;
  }
}
