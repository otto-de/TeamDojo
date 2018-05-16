import { Component, Input, OnInit, OnChanges, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { ITeam } from 'app/shared/model/team.model';
import { TeamsSkillsService } from './teams-skills.service';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Location } from '@angular/common';
import { AchievableSkill, IAchievableSkill } from 'app/shared/model/achievable-skill.model';
import { ITEMS_PER_PAGE } from 'app/shared';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import { TeamsSelectionService } from 'app/teams/teams-selection/teams-selection.service';
import * as moment from 'moment';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { Router } from '@angular/router';

const MAX_ITEMS_PER_PAGE = 1000;

@Component({
    selector: 'jhi-teams-skills',
    templateUrl: './teams-skills.component.html',
    styleUrls: ['teams-skills.scss']
})
export class TeamsSkillsComponent implements OnInit, OnChanges {
    @Input() team: ITeam;
    @Input() skill: IAchievableSkill;
    @Output() onSkillClicked = new EventEmitter<{ iSkill: ISkill; aSkill: AchievableSkill }>();
    @Output() onSkillChanged = new EventEmitter<{ iSkill: ISkill; aSkill: AchievableSkill }>();
    skills: IAchievableSkill[];
    filters: string[];
    page: number;
    links: any;
    itemsPerPage: number;
    totalItems: number;

    constructor(
        private teamsSkillsService: TeamsSkillsService,
        private skillService: SkillService,
        private jhiAlertService: JhiAlertService,
        private parseLinks: JhiParseLinks,
        private teamsSelectionService: TeamsSelectionService,
        private storage: LocalStorageService,
        private location: Location,
        private router: Router
    ) {
        this.skills = [];
        this.filters = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes.team && changes.team.previousValue) {
            this.reloadAll();
            this.ngOnInit();
        }
    }

    ngOnInit() {
        const storedFilters = this.storage.retrieve(this.team.id.toString());
        if (storedFilters) {
            this.filters = storedFilters;
        }
        this.loadAll();
    }

    reloadAll() {
        this.skills = [];
        this.page = 0;
        this.links = {
            last: 0
        };
        this.loadAll();
    }

    loadAll() {
        this.teamsSkillsService
            .queryAchievableSkills(this.team.id, {
                page: this.page,
                size: this.isInSkillDetails() ? MAX_ITEMS_PER_PAGE : this.itemsPerPage,
                filter: this.filters
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
        } else {
            skill.achievedAt = null;
        }
        this.teamsSkillsService.updateAchievableSkill(this.team.id, skill).subscribe(
            (res: HttpResponse<IAchievableSkill>) => {
                skill = res.body;

                this.skillService.find(skill.skillId).subscribe(skillRes => {
                    this.onSkillChanged.emit({
                        iSkill: skillRes.body,
                        aSkill: skill
                    });
                });
            },
            (res: HttpErrorResponse) => {
                console.log(res);
            }
        );
    }

    onFilterClicked(filterName: string) {
        const index = this.filters.indexOf(filterName);
        if (index > -1) {
            this.filters.splice(index, 1);
        } else {
            this.filters.push(filterName);
        }
        this.storage.store(this.team.id.toString(), this.filters);
        this.reloadAll();
    }

    isSameTeamSelected() {
        const selectedTeam = this.teamsSelectionService.selectedTeam;
        return selectedTeam && selectedTeam.id === this.team.id;
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

    isInSkillDetails() {
        return typeof this.skill !== 'undefined' && this.skill !== null;
    }

    handleSkillClicked(s: IAchievableSkill) {
        if (this.isInSkillDetails()) {
            const url = this.router.createUrlTree(['/teams', this.team.shortName, 'skills', s.skillId]).toString();
            this.location.replaceState(url);
            this.skillService.find(s.skillId).subscribe(res => {
                this.onSkillClicked.emit({
                    iSkill: res.body,
                    aSkill: s
                });
            });
        }
    }

    handleSkillChanged(s: IAchievableSkill) {
        this.skills = this.skills.map(skill => {
            return skill.skillId === s.skillId ? s : skill;
        });
    }

    isActiveSkill(s: IAchievableSkill) {
        return typeof this.skill !== 'undefined' && this.skill !== null && this.skill.skillId === s.skillId;
    }
}
