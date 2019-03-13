import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILevel } from 'app/shared/model/level.model';

@Component({
  selector: 'jhi-level-detail',
  templateUrl: './level-detail.component.html'
})
export class LevelDetailComponent implements OnInit {
  level: ILevel;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.data.subscribe(({ level }) => {
      this.level = level.body ? level.body : level;
    });
  }

  previousState() {
    window.history.back();
  }
}
