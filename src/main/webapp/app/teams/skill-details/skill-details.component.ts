import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ITeam } from 'app/shared/model/team.model';
import { ISkill } from 'app/shared/model/skill.model';
import { TeamSkillService } from 'app/entities/team-skill';
import { TeamsService } from 'app/teams/teams.service';
import { IBadge } from 'app/shared/model/badge.model';
import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from 'app/entities/level';
import { BadgeService } from 'app/entities/badge';
import * as moment from 'moment';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { AchievableSkill, IAchievableSkill } from 'app/shared/model/achievable-skill.model';
import { TeamsSkillsService } from 'app/teams/teams-skills.service';
import { TeamsSkillsComponent } from 'app/teams/teams-skills.component';
import { TeamsSelectionService } from 'app/teams/teams-selection/teams-selection.service';
import { SkillDetailsInfoComponent } from 'app/teams/skill-details/skill-details-info/skill-details-info.component';

@Component({
    selector: 'jhi-skill-details',
    templateUrl: './skill-details.component.html',
    styleUrls: ['./skill-details.scss']
})
export class SkillDetailsComponent implements OnInit {
    team: ITeam;

    skill: ISkill;

    achievableSkill: IAchievableSkill;

    @Output() onSkillChanged = new EventEmitter<IAchievableSkill>();

    @ViewChild(TeamsSkillsComponent) skillList;

    @ViewChild(SkillDetailsInfoComponent) skillInfo;

    constructor(
        private route: ActivatedRoute,
        private teamSkillService: TeamSkillService,
        private teamsSkillsService: TeamsSkillsService
    ) {}

    ngOnInit(): void {
        this.route.data.subscribe(({ team, skill }) => {
            this.team = team;
            this.skill = skill;
        });
        this.loadData();
    }

    loadData() {
        this.achievableSkill = new AchievableSkill();
        this.teamsSkillsService.findAchievableSkill(this.team.id, this.skill.id).subscribe(skill => {
            this.achievableSkill = skill;
        });
    }

    onSkillInListChanged(skillObjs) {
        this.skillInfo.onSkillInListchange(skillObjs);
    }

    onSkillInListClicked(skillObjs) {
        this.achievableSkill = skillObjs.aSkill;
        this.skillInfo.onSkillInListClicked(skillObjs);
    }
}
