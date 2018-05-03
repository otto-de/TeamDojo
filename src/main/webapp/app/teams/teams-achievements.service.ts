import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBadge } from 'app/shared/model/badge.model';

export type EntityArrayResponseType = HttpResponse<IBadge[]>;

@Injectable()
export class TeamsAchievementsService {
    private resourceUrl = SERVER_API_URL + 'api/badges';

    constructor(private http: HttpClient) {}

    queryBadges(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBadge[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertBadgeArrayResponse(res));
    }

    private convertBadgeArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
        const jsonResponse: IBadge[] = res.body;
        const body: IBadge[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertBadgeItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to Badge.
     */
    private convertBadgeItemFromServer(badge: IBadge): IBadge {
        const copy: IBadge = Object.assign({}, badge, {
            availableUntil: badge.availableUntil != null ? moment(badge.availableUntil) : badge.availableUntil
        });
        return copy;
    }
}
