import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBadge } from 'app/shared/model/badge.model';

export type EntityResponseType = HttpResponse<IBadge>;
export type EntityArrayResponseType = HttpResponse<IBadge[]>;

@Injectable()
export class BadgeService {
    private resourceUrl = SERVER_API_URL + 'api/badges';

    constructor(private http: HttpClient) {}

    create(badge: IBadge): Observable<EntityResponseType> {
        const copy = this.convert(badge);
        return this.http
            .post<IBadge>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(badge: IBadge): Observable<EntityResponseType> {
        const copy = this.convert(badge);
        return this.http
            .put<IBadge>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBadge>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBadge[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: IBadge = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
        const jsonResponse: IBadge[] = res.body;
        const body: IBadge[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to Badge.
     */
    private convertItemFromServer(badge: IBadge): IBadge {
        const copy: IBadge = Object.assign({}, badge, {
            availableUntil: badge.availableUntil != null ? moment(badge.availableUntil) : badge.availableUntil
        });
        return copy;
    }

    /**
     * Convert a Badge to a JSON which can be sent to the server.
     */
    private convert(badge: IBadge): IBadge {
        const copy: IBadge = Object.assign({}, badge, {
            availableUntil: badge.availableUntil != null && badge.availableUntil.isValid() ? badge.availableUntil.toJSON() : null
        });
        return copy;
    }
}
