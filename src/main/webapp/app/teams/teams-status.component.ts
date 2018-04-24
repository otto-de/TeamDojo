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
}
