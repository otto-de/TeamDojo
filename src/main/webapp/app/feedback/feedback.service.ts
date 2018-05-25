import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReport } from 'app/shared/model/report.model';

export type EntityResponseType = HttpResponse<IReport>;
export type EntityArrayResponseType = HttpResponse<IReport[]>;

@Injectable()
export class FeedbackService {
    private resourceUrl = SERVER_API_URL + 'api/reports';

    constructor(private http: HttpClient) {}

    create(report: IReport): Observable<EntityResponseType> {
        const copy = this.convert(report);
        return this.http
            .post<IReport>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: IReport = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to Feedback.
     */
    private convertItemFromServer(report: IReport): IReport {
        const copy: IReport = Object.assign({}, report, {});
        return copy;
    }

    /**
     * Convert a Feedback to a JSON which can be sent to the server.
     */
    private convert(report: IReport): IReport {
        const copy: IReport = Object.assign({}, report, {});
        return copy;
    }
}
