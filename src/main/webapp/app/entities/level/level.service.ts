import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILevel } from 'app/shared/model/level.model';

export type EntityResponseType = HttpResponse<ILevel>;
export type EntityArrayResponseType = HttpResponse<ILevel[]>;

@Injectable()
export class LevelService {
  private resourceUrl = SERVER_API_URL + 'api/levels';

  constructor(private http: HttpClient) {}

  create(level: ILevel): Observable<EntityResponseType> {
    const copy = this.convert(level);
    return this.http
      .post<ILevel>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  update(level: ILevel): Observable<EntityResponseType> {
    const copy = this.convert(level);
    return this.http
      .put<ILevel>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILevel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILevel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: ILevel = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
    const jsonResponse: ILevel[] = res.body;
    const body: ILevel[] = [];
    for (let i = 0; i < jsonResponse.length; i++) {
      body.push(this.convertItemFromServer(jsonResponse[i]));
    }
    return res.clone({ body });
  }

  /**
   * Convert a returned JSON object to Level.
   */
  private convertItemFromServer(level: ILevel): ILevel {
    const copy: ILevel = Object.assign({}, level, {});
    return copy;
  }

  /**
   * Convert a Level to a JSON which can be sent to the server.
   */
  private convert(level: ILevel): ILevel {
    const copy: ILevel = Object.assign({}, level, {});
    return copy;
  }
}
