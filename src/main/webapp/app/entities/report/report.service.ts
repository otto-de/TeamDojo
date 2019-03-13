import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReport } from 'app/shared/model/report.model';

export type EntityResponseType = HttpResponse<IReport>;
export type EntityArrayResponseType = HttpResponse<IReport[]>;

@Injectable()
export class ReportService {
  private resourceUrl = SERVER_API_URL + 'api/reports';

  constructor(private http: HttpClient) {}

  create(report: IReport): Observable<EntityResponseType> {
    const copy = this.convert(report);
    return this.http
      .post<IReport>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  update(report: IReport): Observable<EntityResponseType> {
    const copy = this.convert(report);
    return this.http
      .put<IReport>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: IReport = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
    const jsonResponse: IReport[] = res.body;
    const body: IReport[] = [];
    for (let i = 0; i < jsonResponse.length; i++) {
      body.push(this.convertItemFromServer(jsonResponse[i]));
    }
    return res.clone({ body });
  }

  /**
   * Convert a returned JSON object to Report.
   */
  private convertItemFromServer(report: IReport): IReport {
    const copy: IReport = Object.assign({}, report, {
      creationDate: report.creationDate != null ? moment(report.creationDate) : report.creationDate
    });
    return copy;
  }

  /**
   * Convert a Report to a JSON which can be sent to the server.
   */
  private convert(report: IReport): IReport {
    const copy: IReport = Object.assign({}, report, {
      creationDate: report.creationDate != null && report.creationDate.isValid() ? report.creationDate.toJSON() : null
    });
    return copy;
  }
}
