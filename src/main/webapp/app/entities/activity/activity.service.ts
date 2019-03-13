import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActivity } from 'app/shared/model/activity.model';

export type EntityResponseType = HttpResponse<IActivity>;
export type EntityArrayResponseType = HttpResponse<IActivity[]>;

@Injectable()
export class ActivityService {
  private resourceUrl = SERVER_API_URL + 'api/activities';

  constructor(private http: HttpClient) {}

  create(activity: IActivity): Observable<EntityResponseType> {
    const copy = this.convert(activity);
    return this.http
      .post<IActivity>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  update(activity: IActivity): Observable<EntityResponseType> {
    const copy = this.convert(activity);
    return this.http
      .put<IActivity>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: IActivity = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
    const jsonResponse: IActivity[] = res.body;
    const body: IActivity[] = [];
    for (let i = 0; i < jsonResponse.length; i++) {
      body.push(this.convertItemFromServer(jsonResponse[i]));
    }
    return res.clone({ body });
  }

  /**
   * Convert a returned JSON object to Activity.
   */
  private convertItemFromServer(activity: IActivity): IActivity {
    const copy: IActivity = Object.assign({}, activity, {
      createdAt: activity.createdAt != null ? moment(activity.createdAt) : activity.createdAt
    });
    return copy;
  }

  /**
   * Convert a Activity to a JSON which can be sent to the server.
   */
  private convert(activity: IActivity): IActivity {
    const copy: IActivity = Object.assign({}, activity, {
      createdAt: activity.createdAt != null && activity.createdAt.isValid() ? activity.createdAt.toJSON() : null
    });
    return copy;
  }
}
