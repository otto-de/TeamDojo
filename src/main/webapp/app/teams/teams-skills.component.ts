import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
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
import { ActivatedRoute, ParamMap, Router } from '@angular/router';

const MAX_ITEMS_PER_PAGE = 1000;
import { BreadcrumbService } from 'app/layouts/navbar/breadcrumb.service';
import { LevelService } from 'app/entities/level';
import { ILevel } from 'app/shared/model/level.model';
import { BadgeService } from 'app/entities/badge';
import { IBadge } from 'app/shared/model/badge.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from 'app/entities/dimension';

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
    levelId: number;
    badgeId: number;
    activeBadge: IBadge;
    activeLevel: ILevel;
    activeDimension: IDimension;
    activeSkill: ISkill;

    constructor(
        private teamsSkillsService: TeamsSkillsService,
        private skillService: SkillService,
        private jhiAlertService: JhiAlertService,
        private parseLinks: JhiParseLinks,
        private teamsSelectionService: TeamsSelectionService,
        private storage: LocalStorageService,
        private route: ActivatedRoute,
        private location: Location,
        private router: Router,
        private breadcrumbService: BreadcrumbService,
        private levelService: LevelService,
        private badgeService: BadgeService,
        private dimensionService: DimensionService
    ) {
        this.filters = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes.team && changes.team.previousValue && changes.team.previousValue.id !== changes.team.currentValue.id) {
            this.reset();
            this.loadAll();
        }
    }

    getLevelId() {
        if (this.levelId !== null && typeof this.levelId !== 'undefined') {
            return this.levelId;
        }
        return '';
    }

    getBadgeId() {
        if (this.badgeId !== null && typeof this.badgeId !== 'undefined') {
            return this.badgeId;
        }
        return '';
    }

    ngOnInit() {
        this.reset();
        this.route.queryParamMap.subscribe((params: ParamMap) => {
            const levelId = this.getParamAsNumber('level', params);
            const badgeId = this.getParamAsNumber('badge', params);
            this.levelId = levelId ? levelId : null;
            this.badgeId = badgeId ? badgeId : null;
            this.reset();
            this.loadAll();
        });
        this.loadAll();
    }

    ngAfterViewInit() {}

    private getParamAsNumber(name: string, params: ParamMap) {
        return Number.parseInt(params.get(name));
    }

    getFiltersFromStorage(): string[] {
        return this.storage.retrieve(this.team.id.toString()) || [];
    }

    reset() {
        this.filters = this.getFiltersFromStorage();
        this.skills = [];
        this.page = 0;
        this.links = {
            last: 0
        };
    }

    loadAll() {
        this.teamsSkillsService
            .queryAchievableSkills(this.team.id, {
                page: this.page,
                size: this.isInSkillDetails() ? MAX_ITEMS_PER_PAGE : this.itemsPerPage,
                filter: this.filters,
                levelId: this.levelId || null,
                badgeId: this.badgeId || null
            })
            .subscribe(
                (res: HttpResponse<IAchievableSkill[]>) => this.paginateAchievableSkills(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        if (this.badgeId !== null && typeof this.badgeId !== 'undefined') {
            this.badgeService.find(this.badgeId).subscribe(badge => {
                this.activeBadge = badge.body;
                this.activeLevel = null;
                this.activeDimension = null;
                this.updateBreadcrumb();
            });
        }

        if (this.levelId !== null && typeof this.levelId !== 'undefined') {
            this.levelService.find(this.levelId).subscribe(level => {
                this.activeBadge = null;
                this.activeLevel = level.body;
                this.dimensionService.find(this.activeLevel.dimensionId).subscribe(dimension => {
                    this.activeDimension = dimension.body;
                    this.updateBreadcrumb();
                });
            });
        }
        if (typeof this.skill !== 'undefined' && this.skill !== null && typeof this.skill.skillId !== 'undefined') {
            this.skillService.find(this.skill.skillId).subscribe(skillRes => {
                this.activeSkill = skillRes.body;
                this.updateBreadcrumb();
            });
        } else {
            this.activeSkill = null;
        }

        this.updateBreadcrumb();
    }

    private updateBreadcrumb() {
        this.breadcrumbService.setBreadcrumb(this.team, this.activeDimension, this.activeLevel, this.activeBadge, this.activeSkill);
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    setComplete(skill: IAchievableSkill) {
        if (!skill.irrelevant) {
            skill.achievedAt = moment();
            this.updateSkill(skill);
        }
    }

    setIncomplete(skill: IAchievableSkill) {
        if (!skill.irrelevant) {
            skill.achievedAt = null;
            this.updateSkill(skill);
        }
    }

    setIrrelevant(skill: IAchievableSkill) {
        skill.irrelevant = true;
        skill.achievedAt = null;
        this.updateSkill(skill);
    }

    setRelevant(skill: IAchievableSkill) {
        skill.irrelevant = false;
        this.updateSkill(skill);
    }

    private updateSkill(skill: IAchievableSkill) {
        this.teamsSkillsService.updateAchievableSkill(this.team.id, skill).subscribe(
            (res: HttpResponse<IAchievableSkill>) => {
                skill = res.body;
                this.skillService.find(skill.skillId).subscribe(skillRes => {
                    this.onSkillChanged.emit({
                        iSkill: skillRes.body,
                        aSkill: skill
                    });
                });
                this.reset();
                this.loadAll();
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
        this.reset();
        this.loadAll();
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
            const url = this.router
                .createUrlTree(['/teams', this.team.shortName, 'skills', s.skillId], {
                    queryParams: { level: this.getLevelId(), badge: this.getBadgeId() }
                })
                .toString();
            this.location.replaceState(url);
            this.skillService.find(s.skillId).subscribe(skill => {
                this.onSkillClicked.emit({
                    iSkill: skill.body,
                    aSkill: s
                });
                this.breadcrumbService.setBreadcrumb(this.team, this.activeDimension, this.activeLevel, this.activeBadge, skill.body);
            });
        }
    }

    handleSkillChanged(s: IAchievableSkill) {
        this.skills = this.skills.map(skill => {
            return skill.skillId === s.skillId ? s : skill;
        });
        this.reset();
        this.loadAll();
    }

    isActiveSkill(s: IAchievableSkill) {
        return typeof this.skill !== 'undefined' && this.skill !== null && this.skill.skillId === s.skillId;
    }
}
