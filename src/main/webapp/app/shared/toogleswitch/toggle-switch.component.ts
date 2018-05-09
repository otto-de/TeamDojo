import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'jhi-toggle-switch',
    templateUrl: './toggle-switch.component.html',
    styleUrls: ['./toggle-switch.scss']
})
export class ToggleSwitchComponent implements OnInit {
    @Output() onToggled = new EventEmitter<boolean>();
    @Input() checked: boolean;
    @Input() popoverText;
    @Input() disabled: boolean;

    constructor() {}

    ngOnInit() {}

    toggle(popover) {
        this.checked = !this.checked;
        this.onToggled.emit(this.checked);
        popover.close();
    }
}
