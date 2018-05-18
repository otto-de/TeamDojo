import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrganization } from 'app/shared/model/organization.model';

export type EntityResponseType = HttpResponse<IOrganization>;
export type EntityArrayResponseType = HttpResponse<IOrganization[]>;

@Injectable()
export class OrganizationService {
    private resourceUrl = SERVER_API_URL + 'api/organizations';

    constructor(private http: HttpClient) {}

    create(organization: IOrganization): Observable<EntityResponseType> {
        const copy = this.convert(organization);
        return this.http
            .post<IOrganization>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(organization: IOrganization): Observable<EntityResponseType> {
        const copy = this.convert(organization);
        return this.http
            .put<IOrganization>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: IOrganization = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: EntityArrayResponseType): EntityArrayResponseType {
        const jsonResponse: IOrganization[] = res.body;
        const body: IOrganization[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to Organization.
     */
    private convertItemFromServer(organization: IOrganization): IOrganization {
        const copy: IOrganization = Object.assign({}, organization, {});
        return copy;
    }

    /**
     * Convert a Organization to a JSON which can be sent to the server.
     */
    private convert(organization: IOrganization): IOrganization {
        const copy: IOrganization = Object.assign({}, organization, {});
        return copy;
    }
}
