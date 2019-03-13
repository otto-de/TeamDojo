import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from './activity.service';

@Component({
  selector: 'jhi-activity-update',
  templateUrl: './activity-update.component.html'
})
export class ActivityUpdateComponent implements OnInit {
  private _activity: IActivity;
  isSaving: boolean;
  createdAt: string;

  constructor(private activityService: ActivityService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.route.data.subscribe(({ activity }) => {
      this.activity = activity.body ? activity.body : activity;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.activity.createdAt = moment(this.createdAt, DATE_TIME_FORMAT);
    if (this.activity.id !== undefined) {
      this.subscribeToSaveResponse(this.activityService.update(this.activity));
    } else {
      this.subscribeToSaveResponse(this.activityService.create(this.activity));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IActivity>>) {
    result.subscribe((res: HttpResponse<IActivity>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(result: IActivity) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get activity() {
    return this._activity;
  }

  set activity(activity: IActivity) {
    this._activity = activity;
    this.createdAt = moment(activity.createdAt).format();
  }
}
