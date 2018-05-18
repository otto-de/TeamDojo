import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from './organization.service';

@Component({
    selector: 'jhi-organization-update',
    templateUrl: './organization-update.component.html'
})
export class OrganizationUpdateComponent implements OnInit {
    private _organization: IOrganization;
    isSaving: boolean;

    constructor(private organizationService: OrganizationService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ organization }) => {
            this.organization = organization.body ? organization.body : organization;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.organization.id !== undefined) {
            this.subscribeToSaveResponse(this.organizationService.update(this.organization));
        } else {
            this.subscribeToSaveResponse(this.organizationService.create(this.organization));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrganization>>) {
        result.subscribe(
            (res: HttpResponse<IOrganization>) => this.onSaveSuccess(res.body),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: IOrganization) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get organization() {
        return this._organization;
    }

    set organization(organization: IOrganization) {
        this._organization = organization;
    }
}
