import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'toggle-switch',
    templateUrl: './toggle-switch.component.html',
    styleUrls: ['./toggle-switch.scss']
})
export class ToggleSwitchComponent implements OnInit {
    @Output() onToggled = new EventEmitter<boolean>();
    @Input() private checked: boolean;
    @Input() private popoverText;

    constructor() {}

    ngOnInit(): void {}

    toggle(popover) {
        this.checked = !this.checked;
        this.onToggled.emit(this.checked);
        popover.close();
    }
}
