import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { IReport } from 'app/shared/model/report.model';
import { FeedbackService } from './feedback.service';
import * as moment from 'moment';

@Component({
    selector: 'jhi-feedback',
    templateUrl: './feedback.component.html'
})
export class FeedbackComponent implements OnInit {
    private _report: IReport;
    isSubmitting: boolean;

    constructor(private feedbackService: FeedbackService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.isSubmitting = false;
        this.route.data.subscribe(({ report }) => {
            this.report = report.body ? report.body : report;
        });
    }

    previousState() {
        window.history.back();
    }

    submit() {
        this.isSubmitting = true;
        this._report.creationDate = moment();
        this.subscribeToSubmitResponse(this.feedbackService.create(this.report));
    }

    private subscribeToSubmitResponse(result: Observable<HttpResponse<IReport>>) {
        result.subscribe((res: HttpResponse<IReport>) => this.onSubmitSuccess(res.body), (res: HttpErrorResponse) => this.onSubmitError());
    }

    private onSubmitSuccess(result: IReport) {
        this.isSubmitting = false;
        this.previousState();
    }

    private onSubmitError() {
        this.isSubmitting = false;
    }
    get report() {
        return this._report;
    }

    set report(report: IReport) {
        this._report = report;
    }
}
