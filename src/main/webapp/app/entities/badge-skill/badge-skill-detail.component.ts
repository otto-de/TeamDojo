import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBadgeSkill } from 'app/shared/model/badge-skill.model';

@Component({
  selector: 'jhi-badge-skill-detail',
  templateUrl: './badge-skill-detail.component.html'
})
export class BadgeSkillDetailComponent implements OnInit {
  badgeSkill: IBadgeSkill;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.data.subscribe(({ badgeSkill }) => {
      this.badgeSkill = badgeSkill.body ? badgeSkill.body : badgeSkill;
    });
  }

  previousState() {
    window.history.back();
  }
}
