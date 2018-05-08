import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TeamsSelectionService } from 'app/teams/teams-selection/teams-selection.service';
import { ITeam } from 'app/shared/model/team.model';

@Component({
    selector: 'jhi-toggle-switch',
    templateUrl: './toggle-switch.component.html',
    styleUrls: ['./toggle-switch.scss']
})
export class ToggleSwitchComponent implements OnInit {
    @Output() onToggled = new EventEmitter<boolean>();
    @Input() private checked: boolean;
    @Input() private popoverText;
    @Input() private disabled: boolean;

    constructor() {}

    ngOnInit() {}

    toggle(popover) {
        this.checked = !this.checked;
        this.onToggled.emit(this.checked);
        popover.close();
    }
}
