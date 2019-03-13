import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITeamSkill } from 'app/shared/model/team-skill.model';

export type EntityResponseType = HttpResponse<ITeamSkill>;
export type EntityArrayResponseType = HttpResponse<ITeamSkill[]>;

@Injectable()
export class TeamSkillService {
  private resourceUrl = SERVER_API_URL + 'api/team-skills';

  constructor(private http: HttpClient) {}

  create(teamSkill: ITeamSkill): Observable<EntityResponseType> {
    const copy = this.convert(teamSkill);
    return this.http
      .post<ITeamSkill>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  update(teamSkill: ITeamSkill): Observable<EntityResponseType> {
    const copy = this.convert(teamSkill);
    return this.http
      .put<ITeamSkill>(this.resourceUrl, copy, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITeamSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .map((res: EntityResponseType) => this.convertResponse(res));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITeamSkill[]>(this.resourceUrl, { params: options, observe: 'response' })
      .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  private convertResponse(res: EntityResponseType): EntityResponseType {
    const body: ITeamSkill = this.convertItemFromServer(res.body);
    return res.clone({ body });
  }

  private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
    const jsonResponse: ITeamSkill[] = res.body;
    const body: ITeamSkill[] = [];
    for (let i = 0; i < jsonResponse.length; i++) {
      body.push(this.convertItemFromServer(jsonResponse[i]));
    }
    return res.clone({ body });
  }

  /**
   * Convert a returned JSON object to TeamSkill.
   */
  private convertItemFromServer(teamSkill: ITeamSkill): ITeamSkill {
    const copy: ITeamSkill = Object.assign({}, teamSkill, {
      completedAt: teamSkill.completedAt != null ? moment(teamSkill.completedAt) : teamSkill.completedAt,
      verifiedAt: teamSkill.verifiedAt != null ? moment(teamSkill.verifiedAt) : teamSkill.verifiedAt
    });
    return copy;
  }

  /**
   * Convert a TeamSkill to a JSON which can be sent to the server.
   */
  private convert(teamSkill: ITeamSkill): ITeamSkill {
    const copy: ITeamSkill = Object.assign({}, teamSkill, {
      completedAt: teamSkill.completedAt != null && teamSkill.completedAt.isValid() ? teamSkill.completedAt.toJSON() : null,
      verifiedAt: teamSkill.verifiedAt != null && teamSkill.verifiedAt.isValid() ? teamSkill.verifiedAt.toJSON() : null
    });
    return copy;
  }
}
