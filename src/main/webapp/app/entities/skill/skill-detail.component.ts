import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISkill } from 'app/shared/model/skill.model';

@Component({
  selector: 'jhi-skill-detail',
  templateUrl: './skill-detail.component.html'
})
export class SkillDetailComponent implements OnInit {
  skill: ISkill;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.data.subscribe(({ skill }) => {
      this.skill = skill.body ? skill.body : skill;
    });
  }

  previousState() {
    window.history.back();
  }
}
