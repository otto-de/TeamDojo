import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBadgeSkill } from 'app/shared/model/badge-skill.model';

export type EntityResponseType = HttpResponse<IBadgeSkill>;
export type EntityArrayResponseType = HttpResponse<IBadgeSkill[]>;

@Injectable()
export class BadgeSkillService {
    private resourceUrl = SERVER_API_URL + 'api/badge-skills';

    constructor(private http: HttpClient) {}

    create(badgeSkill: IBadgeSkill): Observable<EntityResponseType> {
        const copy = this.convert(badgeSkill);
        return this.http
            .post<IBadgeSkill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(badgeSkill: IBadgeSkill): Observable<EntityResponseType> {
        const copy = this.convert(badgeSkill);
        return this.http
            .put<IBadgeSkill>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBadgeSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBadgeSkill[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: IBadgeSkill = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
        const jsonResponse: IBadgeSkill[] = res.body;
        const body: IBadgeSkill[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to BadgeSkill.
     */
    private convertItemFromServer(badgeSkill: IBadgeSkill): IBadgeSkill {
        const copy: IBadgeSkill = Object.assign({}, badgeSkill, {});
        return copy;
    }

    /**
     * Convert a BadgeSkill to a JSON which can be sent to the server.
     */
    private convert(badgeSkill: IBadgeSkill): IBadgeSkill {
        const copy: IBadgeSkill = Object.assign({}, badgeSkill, {});
        return copy;
    }
}
