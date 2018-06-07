import { Component, Input } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';

@Component({
    selector: 'jhi-team-image',
    templateUrl: './team-image.component.html',
    styleUrls: ['./team-image.scss']
})
export class TeamImageComponent {
    @Input() team: ITeam;
    @Input() size = '50px';
    @Input() imageSize = 'large';
    @Input() hasPlaceholder = true;
    @Input() hasBorder = true;
    @Input() hasOverlay = false;
    constructor() {}
}
