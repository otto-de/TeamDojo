import { Component, Input } from '@angular/core';

/**
 * Workaround for the broken jhiTranslate directive which can't handle the translateValues
 * properly.
 */
/* tslint:disable */
@Component({
    selector: '[tdTranslate]',
    template: '<span [innerHTML]="tdTranslate | translate:translateValues"></span>'
})
export class TdTranslateComponent {
    @Input() tdTranslate: string;
    @Input() translateValues: any;
}
/* tslint:enable */
