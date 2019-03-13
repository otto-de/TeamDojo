import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDimension } from 'app/shared/model/dimension.model';

@Component({
  selector: 'jhi-dimension-detail',
  templateUrl: './dimension-detail.component.html'
})
export class DimensionDetailComponent implements OnInit {
  dimension: IDimension;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.data.subscribe(({ dimension }) => {
      this.dimension = dimension.body ? dimension.body : dimension;
    });
  }

  previousState() {
    window.history.back();
  }
}
