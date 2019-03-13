import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrganization } from 'app/shared/model/organization.model';
import { Principal } from 'app/core';
import { OrganizationService } from './organization.service';

@Component({
  selector: 'jhi-organization',
  templateUrl: './organization.component.html'
})
export class OrganizationComponent implements OnInit, OnDestroy {
  organizations: IOrganization[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private organizationService: OrganizationService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.organizationService.query().subscribe(
      (res: HttpResponse<IOrganization[]>) => {
        this.organizations = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOrganizations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOrganization) {
    return item.id;
  }

  registerChangeInOrganizations() {
    this.eventSubscriber = this.eventManager.subscribe('organizationListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
