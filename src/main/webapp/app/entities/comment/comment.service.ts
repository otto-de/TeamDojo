import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IComment } from 'app/shared/model/comment.model';

export type EntityResponseType = HttpResponse<IComment>;
export type EntityArrayResponseType = HttpResponse<IComment[]>;

@Injectable()
export class CommentService {
  private resourceUrl = SERVER_API_URL + 'api/comments';

  constructor(private http: HttpClient) {}

  create(comment: IComment): Observable<EntityResponseType> {
    const copy = this.convert(comment);
    return this.http
      .post<IComment>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  update(comment: IComment): Observable<EntityResponseType> {
    const copy = this.convert(comment);
    return this.http
      .put<IComment>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IComment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IComment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: IComment = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
    const jsonResponse: IComment[] = res.body;
    const body: IComment[] = [];
    for (let i = 0; i < jsonResponse.length; i++) {
      body.push(this.convertItemFromServer(jsonResponse[i]));
    }
    return res.clone({ body });
  }

  /**
   * Convert a returned JSON object to Comment.
   */
  private convertItemFromServer(comment: IComment): IComment {
    const copy: IComment = Object.assign({}, comment, {
      creationDate: comment.creationDate != null ? moment(comment.creationDate) : comment.creationDate
    });
    return copy;
  }

  /**
   * Convert a Comment to a JSON which can be sent to the server.
   */
  private convert(comment: IComment): IComment {
    const copy: IComment = Object.assign({}, comment, {
      creationDate: comment.creationDate != null && comment.creationDate.isValid() ? comment.creationDate.toJSON() : null
    });
    return copy;
  }
}
