import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDimension } from 'app/shared/model/dimension.model';
import { Principal } from 'app/core';
import { DimensionService } from './dimension.service';

@Component({
  selector: 'jhi-dimension',
  templateUrl: './dimension.component.html'
})
export class DimensionComponent implements OnInit, OnDestroy {
  dimensions: IDimension[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private dimensionService: DimensionService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.dimensionService.query().subscribe(
      (res: HttpResponse<IDimension[]>) => {
        this.dimensions = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDimensions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDimension) {
    return item.id;
  }

  registerChangeInDimensions() {
    this.eventSubscriber = this.eventManager.subscribe('dimensionListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
