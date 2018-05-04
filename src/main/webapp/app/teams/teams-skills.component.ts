import { Component, Input, OnInit } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';
import { TeamsSkillsService } from './teams-skills.service';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { IAchievableSkill } from 'app/shared/model/achievable-skill.model';
import { ITEMS_PER_PAGE } from 'app/shared';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import moment = require('moment');

@Component({
    selector: 'jhi-teams-skills',
    templateUrl: './teams-skills.component.html',
    styleUrls: ['teams-skills.scss']
})
export class TeamsSkillsComponent implements OnInit {
    @Input() team: ITeam;
    skills: IAchievableSkill[];
    page: number;
    links: any;
    itemsPerPage: number;
    totalItems: number;

    constructor(
        private teamsSkillsService: TeamsSkillsService,
        private jhiAlertService: JhiAlertService,
        private parseLinks: JhiParseLinks
    ) {
        this.skills = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
    }

    ngOnInit() {
        this.loadAll();
    }

    loadAll() {
        this.teamsSkillsService
            .queryAchievableSkills(this.team.id, {
                page: this.page,
                size: this.itemsPerPage
            })
            .subscribe(
                (res: HttpResponse<IAchievableSkill[]>) => this.paginateAchievableSkills(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reset() {
        this.page = 0;
        this.skills = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    onToggled(checked: boolean, skill: IAchievableSkill) {
        if (checked) {
            skill.achievedAt = moment();
            this.teamsSkillsService.addTeamSkill(this.team.id, skill.skillId);
        } else {
            skill.achievedAt = null;
            this.teamsSkillsService.removeTeamSkill(this.team.id, skill.skillId);
        }
    }

    private paginateAchievableSkills(data: IAchievableSkill[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.skills.push(data[i]);
        }
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
