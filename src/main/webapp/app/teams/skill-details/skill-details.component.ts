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

@Component({
    selector: 'jhi-skill-details',
    templateUrl: './skill-details.component.html',
    styleUrls: ['./skill-details.scss']
})
export class SkillDetailsComponent implements OnInit {
    team: ITeam;

    skill: ISkill;

    achievableSkill: IAchievableSkill;

    achievedByTeams: ITeam[] = [];

    neededForLevels: ILevel[] = [];

    neededForBadges: IBadge[] = [];

    @Output() onSkillChanged = new EventEmitter<IAchievableSkill>();

    @ViewChild(TeamsSkillsComponent) skillList;

    constructor(
        private route: ActivatedRoute,
        private teamSkillService: TeamSkillService,
        private teamsSkillsService: TeamsSkillsService,
        private teamsSelectionService: TeamsSelectionService,
        private teamsService: TeamsService,
        private levelService: LevelService,
        private badgeService: BadgeService
    ) {}

    ngOnInit(): void {
        this.route.data.subscribe(({ team, skill }) => {
            this.team = team;
            this.skill = skill;
        });
        this.loadData();
    }

    loadData() {
        this.achievedByTeams = [];
        this.neededForLevels = [];
        this.neededForBadges = [];
        this.achievableSkill = new AchievableSkill();

        this.teamSkillService.query({ 'skillId.equals': this.skill.id, 'completedAt.specified': true }).subscribe(res => {
            const teamsId = res.body.map(ts => ts.teamId);
            if (teamsId.length !== 0) {
                this.teamsService.query({ 'id.in': teamsId }).subscribe(teamsRes => {
                    this.achievedByTeams = teamsRes.body;
                });
            }
        });

        this.teamsSkillsService.findAchievableSkill(this.team.id, this.skill.id).subscribe(skill => {
            this.achievableSkill = skill;
        });

        this.levelService.query({ 'skillsId.in': this.skill.id }).subscribe(res => {
            this.neededForLevels = res.body;
        });

        this.badgeService.query({ 'skillsId.in': this.skill.id }).subscribe(res => {
            this.neededForBadges = res.body;
        });
    }

    onSkillInListChange(skillObjs) {
        this.achievableSkill = skillObjs.aSkill;
        this.skill = skillObjs.iSkill;
    }

    onSkillSelected(skillObjs) {
        this.achievableSkill = skillObjs.aSkill;
        this.skill = skillObjs.iSkill;
    }

    onToggleSkill(isActivated: boolean) {
        this.achievableSkill.achievedAt = isActivated ? moment() : null;
        this.updateSkill();
    }

    updateSkill() {
        this.teamsSkillsService.updateAchievableSkill(this.team.id, this.achievableSkill).subscribe(
            (res: HttpResponse<IAchievableSkill>) => {
                this.achievableSkill = res.body;
                this.onSkillChanged.emit(this.achievableSkill);
                this.skillList.handleSkillChanged(this.achievableSkill);
            },
            (res: HttpErrorResponse) => {
                console.log(res);
            }
        );
    }

    skillAchieved() {
        return this.achievableSkill && !!this.achievableSkill.achievedAt;
    }

    isSameTeamSelected() {
        const selectedTeam = this.teamsSelectionService.selectedTeam;
        return selectedTeam && selectedTeam.id === this.team.id;
    }
}
