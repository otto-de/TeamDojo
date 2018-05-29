import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { ISkill } from 'app/shared/model/skill.model';
import { ITeam } from 'app/shared/model/team.model';

@Component({
    selector: 'star-rating',
    templateUrl: './skill-details-rating.component.html',
    styleUrls: ['./skill-details-rating.scss']
})
export class SkillDetailsRatingComponent implements OnInit {
    ngOnInit() {
        debugger;
        console.log('In SkillDetailsRatingComponent');
        console.log('Was geht ab');
    }
}
