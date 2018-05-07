import { Component, Input } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';

@Component({
    selector: 'jhi-teams-status',
    templateUrl: './teams-status.component.html',
    styleUrls: ['teams-status.scss']
})
export class TeamsStatusComponent {
    @Input() team: ITeam;

    constructor() {}

    get teamImage() {
        return this.team.picture && this.team.pictureContentType
            ? `data:${this.team.pictureContentType};base64,${this.team.picture}`
            : null;
    }
}
