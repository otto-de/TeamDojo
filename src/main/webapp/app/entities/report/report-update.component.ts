import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IReport } from 'app/shared/model/report.model';
import { ReportService } from './report.service';

@Component({
  selector: 'jhi-report-update',
  templateUrl: './report-update.component.html'
})
export class ReportUpdateComponent implements OnInit {
  private _report: IReport;
  isSaving: boolean;
  creationDate: string;

  constructor(private reportService: ReportService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.route.data.subscribe(({ report }) => {
      this.report = report.body ? report.body : report;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.report.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
    if (this.report.id !== undefined) {
      this.subscribeToSaveResponse(this.reportService.update(this.report));
    } else {
      this.subscribeToSaveResponse(this.reportService.create(this.report));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IReport>>) {
    result.subscribe((res: HttpResponse<IReport>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess(result: IReport) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get report() {
    return this._report;
  }

  set report(report: IReport) {
    this._report = report;
    this.creationDate = moment(report.creationDate).format();
  }
}
