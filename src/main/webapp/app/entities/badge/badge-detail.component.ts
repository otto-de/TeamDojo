import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBadge } from 'app/shared/model/badge.model';

@Component({
  selector: 'jhi-badge-detail',
  templateUrl: './badge-detail.component.html'
})
export class BadgeDetailComponent implements OnInit {
  badge: IBadge;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.data.subscribe(({ badge }) => {
      this.badge = badge.body ? badge.body : badge;
    });
  }

  previousState() {
    window.history.back();
  }
}
