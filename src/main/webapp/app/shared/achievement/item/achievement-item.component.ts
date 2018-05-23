import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IBadge } from 'app/shared/model/badge.model';
import { ILevel } from 'app/shared/model/level.model';

@Component({
    selector: 'jhi-achievement-item',
    templateUrl: './achievement-item.component.html',
    styleUrls: ['./achievement-item.scss']
})
export class AchievementItemComponent {
    @Input() item: any;
    @Input() progress: number;
    @Input() active: boolean;
    @Input() type = '';
    @Input() hasStatus = false;
    @Input() size = '10vh';
    @Output() onItemSelected = new EventEmitter<ILevel | IBadge>();

    selectItem(event) {
        event.preventDefault();
        this.onItemSelected.emit(this.item);
    }

    get itemStatusCssClass() {
        const itemStatus = this.progress >= 100 ? 'complete' : 'incomplete';
        return this.hasStatus ? (this.type ? `${this.type}-${itemStatus}` : `item-${itemStatus}`) : '';
    }
}
