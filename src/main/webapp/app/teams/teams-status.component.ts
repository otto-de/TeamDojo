import { Component, OnInit, Input } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';

@Component({
    selector: 'jhi-teams-status',
    templateUrl: './teams-status.component.html',
    styleUrls: ['teams-status.scss']
})
export class TeamsStatusComponent implements OnInit {
    @Input() team: ITeam;
    private _teamImage;

    constructor() {}

    ngOnInit() {}

    get teamImage() {
        if (!this._teamImage && this.team.picture && this.team.pictureContentType) {
            this._teamImage = `data:${this.team.pictureContentType};base64,${this.team.picture}`;
        }
        return this._teamImage;
    }
}
