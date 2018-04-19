import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILevelSkill } from 'app/shared/model/level-skill.model';

@Component({
    selector: 'jhi-level-skill-detail',
    templateUrl: './level-skill-detail.component.html'
})
export class LevelSkillDetailComponent implements OnInit {
    levelSkill: ILevelSkill;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ levelSkill }) => {
            this.levelSkill = levelSkill.body ? levelSkill.body : levelSkill;
        });
    }

    previousState() {
        window.history.back();
    }
}
