import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISkill } from 'app/shared/model/skill.model';
import { Observable } from 'rxjs/Observable';

export type EntityArrayResponseType = HttpResponse<ISkill[]>;

@Injectable()
export class TeamsSkillsService {
    private resourceUrl = SERVER_API_URL + 'api/skills';

    constructor(private http: HttpClient) {}

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISkill[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
    }

    private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
        const jsonResponse: ISkill[] = res.body;
        const body: ISkill[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to Skill.
     */
    private convertItemFromServer(skill: ISkill): ISkill {
        const copy: ISkill = Object.assign({}, skill, {});
        return copy;
    }

    /**
     * Convert a Skill to a JSON which can be sent to the server.
     */
    private convert(skill: ISkill): ISkill {
        const copy: ISkill = Object.assign({}, skill, {});
        return copy;
    }
}
