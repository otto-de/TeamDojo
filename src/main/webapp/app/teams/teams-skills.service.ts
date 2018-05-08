import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { Observable } from 'rxjs/Observable';
import { IAchievableSkill } from 'app/shared/model/achievable-skill.model';
import * as moment from 'moment';

export type EntityArrayResponseType = HttpResponse<IAchievableSkill[]>;
export type EntityResponseType = HttpResponse<IAchievableSkill>;

@Injectable()
export class TeamsSkillsService {
    private resourceUrl = SERVER_API_URL + 'api/teams';

    constructor(private http: HttpClient) {}

    queryAchievableSkills(teamId: number, req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAchievableSkill[]>(`${this.resourceUrl}/${teamId}/achievable-skills`, {
                params: options,
                observe: 'response'
            })
            .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
    }

    updateAchievableSkill(teamId: number, skill: IAchievableSkill): Observable<EntityResponseType> {
        const copy = this.convert(skill);
        return this.http
            .put<IAchievableSkill>(`${this.resourceUrl}/${teamId}/achievable-skills`, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: IAchievableSkill = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
        const jsonResponse: IAchievableSkill[] = res.body;
        const body: IAchievableSkill[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to Skill.
     */
    private convertItemFromServer(achievableSkill: IAchievableSkill): IAchievableSkill {
        const copy: IAchievableSkill = Object.assign({}, achievableSkill, {
            achievedAt: achievableSkill.achievedAt != null ? moment(achievableSkill.achievedAt) : achievableSkill.achievedAt
        });
        return copy;
    }

    /**
     * Convert a Skill to a JSON which can be sent to the server.
     */
    private convert(achievableSkill: IAchievableSkill): IAchievableSkill {
        const copy: IAchievableSkill = Object.assign({}, achievableSkill, {
            completedAt:
                achievableSkill.achievedAt != null && achievableSkill.achievedAt.isValid() ? achievableSkill.achievedAt.toJSON() : null
        });
        return copy;
    }
}
