import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IBadge } from 'app/shared/model/badge.model';
import { ISkill } from 'app/shared/model/skill.model';

@Component({
    selector: 'jhi-achievement-item',
    templateUrl: './achievement-item.component.html',
    styleUrls: ['./achievement-item.scss']
})
export class AchievementItemComponent implements OnInit {
    @Input() item: any;
    @Input() progress: number;
    @Input() active: boolean;
    @Output() onItemSelected = new EventEmitter<IBadge | ISkill>();

    ngOnInit(): void {}

    itemSelected(event) {
        this.onItemSelected.emit(this.item);
    }
}
