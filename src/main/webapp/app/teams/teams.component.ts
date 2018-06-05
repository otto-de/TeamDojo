import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { JhiDataUtils } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { ITeam } from 'app/shared/model/team.model';
import { sortLevels } from 'app/shared';
import { IBadge } from 'app/shared/model/badge.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { IBadgeSkill } from 'app/shared/model/badge-skill.model';
import { TeamSkillService } from 'app/entities/team-skill';
import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { ISkill } from 'app/shared/model/skill.model';

@Component({
    selector: 'jhi-teams',
    templateUrl: './teams.component.html',
    styleUrls: ['./teams.scss']
})
export class TeamsComponent implements OnInit {
    @Output() changeTeam = new EventEmitter<any>();

    team: ITeam;
    teamSkills: ITeamSkill[];
    badges: IBadge[];
    skills: ISkill[];

    constructor(private dataUtils: JhiDataUtils, private route: ActivatedRoute, private teamSkillService: TeamSkillService) {}

    ngOnInit() {
        this.route.data.subscribe(({ dojoModel: { teams, levels, levelSkills, badges, badgeSkills }, team, skills }) => {
            const teamFromRoute = team && team.body ? team.body : team;
            this.team = (teams || []).find(t => t.id === teamFromRoute.id) || teamFromRoute;
            this.teamSkills = team && team.skills ? team.skills : [];
            this.badges = (badges && badges.body ? badges.body : badges) || [];
            this.skills = (skills && skills.body ? skills.body : skills) || [];
        });
        this.changeTeam.emit(this.team);
    }

    loadTeamSkills() {
        this.teamSkillService.query({ 'teamId.equals': this.team.id }).subscribe(teamSkillResponse => {
            this.team.skills = this.teamSkills = teamSkillResponse.body;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    previousState() {
        window.history.back();
    }
}
