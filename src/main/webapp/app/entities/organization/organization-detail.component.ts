import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganization } from 'app/shared/model/organization.model';

@Component({
  selector: 'jhi-organization-detail',
  templateUrl: './organization-detail.component.html'
})
export class OrganizationDetailComponent implements OnInit {
  organization: IOrganization;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.data.subscribe(({ organization }) => {
      this.organization = organization.body ? organization.body : organization;
    });
  }

  previousState() {
    window.history.back();
  }
}
