import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILevelSkill } from 'app/shared/model/level-skill.model';

export type EntityResponseType = HttpResponse<ILevelSkill>;
export type EntityArrayResponseType = HttpResponse<ILevelSkill[]>;

@Injectable()
export class LevelSkillService {
    private resourceUrl = SERVER_API_URL + 'api/level-skills';

    constructor(private http: HttpClient) {}

    create(levelSkill: ILevelSkill): Observable<EntityResponseType> {
        const copy = this.convert(levelSkill);
        return this.http
            .post<ILevelSkill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(levelSkill: ILevelSkill): Observable<EntityResponseType> {
        const copy = this.convert(levelSkill);
        return this.http
            .put<ILevelSkill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILevelSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILevelSkill[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ILevelSkill = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
        const jsonResponse: ILevelSkill[] = res.body;
        const body: ILevelSkill[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to LevelSkill.
     */
    private convertItemFromServer(levelSkill: ILevelSkill): ILevelSkill {
        const copy: ILevelSkill = Object.assign({}, levelSkill, {});
        return copy;
    }

    /**
     * Convert a LevelSkill to a JSON which can be sent to the server.
     */
    private convert(levelSkill: ILevelSkill): ILevelSkill {
        const copy: ILevelSkill = Object.assign({}, levelSkill, {});
        return copy;
    }
}
