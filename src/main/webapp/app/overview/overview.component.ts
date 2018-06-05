import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ITeam } from 'app/shared/model/team.model';
import { ILevel } from 'app/shared/model/level.model';
import { IBadge } from 'app/shared/model/badge.model';
import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { sortLevels } from 'app/shared';
import { IBadgeSkill } from 'app/shared/model/badge-skill.model';
import { ISkill } from 'app/shared/model/skill.model';

@Component({
    selector: 'jhi-overview',
    templateUrl: './overview.component.html',
    styleUrls: ['./overview.scss']
})
export class OverviewComponent implements OnInit {
    teams: ITeam[];
    levels: ILevel[];
    badges: IBadge[];
    skills: ISkill[];
    selectedTeam: ITeam;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ dojoModel: { teams, levels, badges }, skills, selectedTeam }) => {
            this.teams = (teams && teams.body ? teams.body : teams) || [];
            this.levels = (levels && levels.body ? levels.body : levels) || [];
            this.badges = (badges && badges.body ? badges.body : badges) || [];
            this.skills = (skills && skills.body ? skills.body : skills) || [];
            this.selectedTeam = (selectedTeam && selectedTeam.body ? selectedTeam.body : selectedTeam) || {};
        });
    }
}
