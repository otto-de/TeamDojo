import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDimension } from 'app/shared/model/dimension.model';

export type EntityResponseType = HttpResponse<IDimension>;
export type EntityArrayResponseType = HttpResponse<IDimension[]>;

@Injectable()
export class DimensionService {
  private resourceUrl = SERVER_API_URL + 'api/dimensions';

  constructor(private http: HttpClient) {}

  create(dimension: IDimension): Observable<EntityResponseType> {
    const copy = this.convert(dimension);
    return this.http
      .post<IDimension>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  update(dimension: IDimension): Observable<EntityResponseType> {
    const copy = this.convert(dimension);
    return this.http
      .put<IDimension>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDimension>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDimension[]>(this.resourceUrl, { params: options, observe: 'response' })
      .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: IDimension = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
    const jsonResponse: IDimension[] = res.body;
    const body: IDimension[] = [];
    for (let i = 0; i < jsonResponse.length; i++) {
      body.push(this.convertItemFromServer(jsonResponse[i]));
    }
    return res.clone({ body });
  }

  /**
   * Convert a returned JSON object to Dimension.
   */
  private convertItemFromServer(dimension: IDimension): IDimension {
    const copy: IDimension = Object.assign({}, dimension, {});
    return copy;
  }

  /**
   * Convert a Dimension to a JSON which can be sent to the server.
   */
  private convert(dimension: IDimension): IDimension {
    const copy: IDimension = Object.assign({}, dimension, {});
    return copy;
  }
}
