import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { TeamService } from 'app/entities/team';
import { ITeam } from 'app/shared/model/team.model';
import { TeamsSkillsService } from 'app/teams/teams-skills.service';
import { ILevel } from 'app/shared/model/level.model';
import { IBadge } from 'app/shared/model/badge.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { IBadgeSkill } from 'app/shared/model/badge-skill.model';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { LevelSkillService } from 'app/entities/level-skill';
import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { Location } from '@angular/common';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { BreadcrumbService } from 'app/layouts/navbar/breadcrumb.service';
import { DimensionService } from 'app/entities/dimension';
import { Progress } from 'app/shared/achievement/model/progress.model';
import 'simplebar';
@Component({
    selector: 'jhi-overview-skills',
    templateUrl: './overview-skills.component.html',
    styleUrls: ['./overview-skills.scss']
})
export class OverviewSkillsComponent implements OnInit, OnChanges {
    @Input() activeSkill: ISkill;
    @Output() onSkillChanged = new EventEmitter<ISkill>();
    teams: ITeam[];
    levels: ILevel[];
    levelSkills: ILevelSkill[];
    badges: IBadge[];
    badgeSkills: IBadgeSkill[];
    skills: ISkill[];
    activeSkills: ILevelSkill[] | IBadgeSkill[];
    activeLevel: ILevel;
    activeBadge: IBadge;
    dimensions: IDimension[];
    dimensionsBySkillId: any;
    generalSkillsIds: number[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private route: ActivatedRoute,
        private router: Router,
        private location: Location,
        private breadcrumbService: BreadcrumbService,
        private dimensionService: DimensionService
    ) {}

    ngOnInit() {
        this.route.data.subscribe(({ overview: { teams, levels, levelSkills, badges, badgeSkills }, skills }) => {
            this.teams = teams || [];
            this.levels = levels || [];
            this.levelSkills = levelSkills || [];
            this.badges = badges || [];
            this.badgeSkills = badgeSkills || [];
            this.skills = (skills && skills.body ? skills.body : skills) || [];
            this.route.queryParamMap.subscribe((params: ParamMap) => {
                this.activeLevel = null;
                this.activeBadge = null;
                if (params.get('level')) {
                    this.activeLevel = (this.levels || []).find((level: ILevel) => level.id === Number.parseInt(params.get('level')));
                    this.activeSkills = this.activeLevel ? this.activeLevel.skills : [];
                    this.updateBreadcrumb();
                } else if (params.get('badge')) {
                    this.activeBadge = (this.badges || []).find((badge: IBadge) => badge.id === Number.parseInt(params.get('badge')));
                    this.activeSkills = this.activeBadge ? this.activeBadge.skills : [];
                    this.updateBreadcrumb();
                } else {
                    this.activeSkills = (this.levelSkills || []).concat(...(this.badgeSkills || []));
                }
            });
            this.loadAll();
        });
    }

    loadAll() {
        this.generalSkillsIds = [];
        this.dimensionsBySkillId = {};
        (this.levels || []).forEach(level => {
            (level.skills || []).forEach((levelSkill: ILevelSkill) => {
                const skillId = levelSkill.skillId;
                this.dimensionsBySkillId[skillId] = this.dimensionsBySkillId[skillId] || [];
                if (this.dimensionsBySkillId[skillId].indexOf(level.dimensionId) === -1) {
                    this.dimensionsBySkillId[skillId].push(level.dimensionId);
                }
            });
        });

        (this.badges || []).forEach(badge => {
            if (badge.dimensions.length === 0) {
                this.generalSkillsIds = this.generalSkillsIds.concat((badge.skills || []).map(bs => bs.skillId));
            }

            (badge.dimensions || []).forEach(dimension => {
                (badge.skills || []).forEach((badgeSkill: IBadgeSkill) => {
                    const skillId = badgeSkill.skillId;
                    this.dimensionsBySkillId[skillId] = this.dimensionsBySkillId[skillId] || [];

                    this.dimensionsBySkillId[skillId].forEach(entry => {
                        if (entry.indexOf(skillId) === -1) {
                            this.dimensionsBySkillId[skillId].push(dimension.id);
                        }
                    });
                });
            });
        });
    }

    ngOnChanges(changes: SimpleChanges) {
        this.updateBreadcrumb();
        this.onSkillChanged.emit(this.activeSkill);
    }

    private updateBreadcrumb() {
        if (this.activeLevel !== null && typeof this.activeLevel !== 'undefined') {
            this.dimensionService.find(this.activeLevel.dimensionId).subscribe(dimension => {
                this.breadcrumbService.setBreadcrumb(null, dimension.body, this.activeLevel, this.activeBadge, this.activeSkill);
            });
        } else {
            this.breadcrumbService.setBreadcrumb(null, null, this.activeLevel, this.activeBadge, this.activeSkill);
        }
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    getRelevantTeams(itemSkill: ILevelSkill | IBadgeSkill): string {
        const countProgress = new Progress(0, 0);
        for (const team of this.teams) {
            const teamSkill = this.findTeamSkill(team, itemSkill);
            if (this.isRelevantSkill(team, teamSkill, itemSkill)) {
                countProgress.required++;
                if (this.isTeamSkillCompleted(teamSkill)) {
                    countProgress.achieved++;
                }
            }
        }
        if (this.generalSkillsIds.indexOf(itemSkill.id) !== -1) {
            countProgress.required = this.teams.length;
        }
        return `${countProgress.achieved}  / ${countProgress.required}`;
    }

    private isRelevantSkill(team: ITeam, teamSkill: ITeamSkill, itemSkill: ILevelSkill | IBadgeSkill) {
        if (teamSkill && teamSkill.irrelevant) {
            return false;
        }
        const skillDimensionIds = this.dimensionsBySkillId[itemSkill.skillId] || [];
        return team.participations.some(dimension => {
            return skillDimensionIds.indexOf(dimension.id) !== -1;
        });
    }

    findSkill(skillId: number): ISkill {
        if (this.skills === null || typeof this.skills === 'undefined') {
            return null;
        }

        for (const skill of this.skills) {
            if (skill.id === skillId) {
                return skill;
            }
        }
        return null;
    }

    private findTeamSkill(team: ITeam, skill: ILevelSkill | IBadgeSkill): ITeamSkill {
        return team.skills ? team.skills.find((teamSkill: ITeamSkill) => teamSkill.skillId === skill.skillId) : null;
    }

    private isTeamSkillCompleted(teamSkill: ITeamSkill): boolean {
        return teamSkill && !!teamSkill.completedAt;
    }

    isActiveSkill(iLevelSkill: ILevelSkill) {
        return typeof this.activeSkill !== 'undefined' && this.activeSkill !== null && this.activeSkill.id === iLevelSkill.skillId;
    }

    getRateCount(rateCount: number) {
        return rateCount !== null && typeof rateCount !== 'undefined' ? rateCount : 0;
    }
}
