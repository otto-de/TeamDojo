import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReport } from 'app/shared/model/report.model';

@Component({
  selector: 'jhi-report-detail',
  templateUrl: './report-detail.component.html'
})
export class ReportDetailComponent implements OnInit {
  report: IReport;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.data.subscribe(({ report }) => {
      this.report = report.body ? report.body : report;
    });
  }

  previousState() {
    window.history.back();
  }
}
