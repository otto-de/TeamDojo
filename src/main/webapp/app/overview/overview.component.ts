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
        this.route.data.subscribe(({ teams, levels, badges, skills, selectedTeam }) => {
            this.teams = teams.body;
            this.levels = levels.body;
            this.badges = badges.body;
            this.skills = skills.body;
            this.selectedTeam = selectedTeam && selectedTeam.body ? selectedTeam.body : {};
        });
    }
}
