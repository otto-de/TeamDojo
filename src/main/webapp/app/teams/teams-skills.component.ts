import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { SessionStorageService } from 'ngx-webstorage';
import { ITeam } from 'app/shared/model/team.model';
import { TeamsSkillsService } from './teams-skills.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Location } from '@angular/common';
import { AchievableSkill, IAchievableSkill } from 'app/shared/model/achievable-skill.model';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import { TeamsSelectionService } from 'app/teams/teams-selection/teams-selection.service';
import * as moment from 'moment';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { BreadcrumbService } from 'app/layouts/navbar/breadcrumb.service';
import { LevelService } from 'app/entities/level';
import { ILevel } from 'app/shared/model/level.model';
import { BadgeService } from 'app/entities/badge';
import { IBadge } from 'app/shared/model/badge.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from 'app/entities/dimension';
import 'simplebar';
@Component({
    selector: 'jhi-teams-skills',
    templateUrl: './teams-skills.component.html',
    styleUrls: ['teams-skills.scss']
})
export class TeamsSkillsComponent implements OnInit, OnChanges {
    @Input() team: ITeam;
    @Input() skill: IAchievableSkill;
    @Input() iSkills: ISkill[];
    @Output() onSkillClicked = new EventEmitter<{ iSkill: ISkill; aSkill: AchievableSkill }>();
    @Output() onSkillChanged = new EventEmitter<{ iSkill: ISkill; aSkill: AchievableSkill }>();
    skills: IAchievableSkill[];
    filters: string[];
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
        private storage: SessionStorageService,
        private route: ActivatedRoute,
        private location: Location,
        private router: Router,
        private breadcrumbService: BreadcrumbService,
        private levelService: LevelService,
        private badgeService: BadgeService,
        private dimensionService: DimensionService
    ) {}

    ngOnInit() {
        this.filters = this.getFiltersFromStorage();
        this.skills = [];
        this.route.queryParamMap.subscribe((params: ParamMap) => {
            const levelId = this.getParamAsNumber('level', params);
            const badgeId = this.getParamAsNumber('badge', params);
            this.levelId = levelId ? levelId : null;
            this.badgeId = badgeId ? badgeId : null;
            this.loadAll();
        });
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes.team && changes.team.previousValue && changes.team.previousValue.id !== changes.team.currentValue.id) {
            this.loadAll();
        }
    }

    private getParamAsNumber(name: string, params: ParamMap) {
        return Number.parseInt(params.get(name));
    }

    loadAll() {
        this.teamsSkillsService
            .queryAchievableSkills(this.team.id, {
                filter: this.filters,
                levelId: this.levelId || null,
                badgeId: this.badgeId || null
            })
            .subscribe(
                (res: HttpResponse<IAchievableSkill[]>) => (this.skills = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.activeBadge = null;
        this.activeLevel = null;
        this.activeDimension = null;
        this.activeSkill = null;

        if (this.badgeId) {
            this.badgeService.find(this.badgeId).subscribe(badge => {
                this.activeBadge = badge.body;
                this.updateBreadcrumb();
            });
        }

        if (this.levelId) {
            this.levelService.find(this.levelId).subscribe(level => {
                this.activeLevel = level.body;
                this.dimensionService.find(this.activeLevel.dimensionId).subscribe(dimension => {
                    this.activeDimension = dimension.body;
                    this.updateBreadcrumb();
                });
            });
        }

        if (this.skill && this.skill.skillId) {
            this.skillService.find(this.skill.skillId).subscribe(skillRes => {
                this.activeSkill = skillRes.body;
                this.updateBreadcrumb();
            });
        }

        this.updateBreadcrumb();
    }

    goToDetails(skill: IAchievableSkill) {
        const queryParams = {};
        if (this.levelId) {
            queryParams['level'] = this.levelId;
        }
        if (this.badgeId) {
            queryParams['badge'] = this.badgeId;
        }
        this.router.navigate(['teams', this.team.shortName, 'skills', skill.skillId], {
            queryParams
        });
    }

    private updateBreadcrumb() {
        this.breadcrumbService.setBreadcrumb(this.team, this.activeDimension, this.activeLevel, this.activeBadge, this.activeSkill);
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
        this.storage.store('filterKey', this.filters);
        this.loadAll();
    }

    isSameTeamSelected() {
        const selectedTeam = this.teamsSelectionService.selectedTeam;
        return selectedTeam && selectedTeam.id === this.team.id;
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
                    queryParams: { level: this.levelId || '', badge: this.badgeId || '' }
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

    isActiveSkill(s: IAchievableSkill) {
        return this.skill && this.skill.skillId === s.skillId;
    }

    handleSkillChanged(s: IAchievableSkill) {
        this.updateSkill(s);
        this.skills = this.skills.map(skill => {
            return skill.skillId === s.skillId ? s : skill;
        });
        this.loadAll();
    }

    getRateCount(rateCount: number) {
        return rateCount !== null && typeof rateCount !== 'undefined' ? rateCount : 0;
    }

    private getFiltersFromStorage(): string[] {
        return this.storage.retrieve('filterKey') || [];
    }
}
