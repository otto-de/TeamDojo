import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { ISkill } from 'app/shared/model/skill.model';
import { ITeam } from 'app/shared/model/team.model';

@Component({
    selector: 'jhi-star-rating',
    templateUrl: './skill-details-rating.component.html',
    styleUrls: ['./skill-details-rating.scss']
})
export class SkillDetailsRatingComponent {
    private currentRate = 4.5;
}
