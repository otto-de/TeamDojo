import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
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
import { ActivatedRoute, ParamMap } from '@angular/router';
import { LevelSkillService } from 'app/entities/level-skill';
import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';

@Component({
    selector: 'jhi-overview-skills',
    templateUrl: './overview-skills.component.html',
    styleUrls: ['./overview-skills.scss']
})
export class OverviewSkillsComponent implements OnInit {
    @Input() teams: ITeam[];
    @Input() levels: ILevel[];
    @Input() badges: IBadge[];
    @Input() activeSkill: ISkill;
    @Output() onSkillClicked = new EventEmitter<{ iSkill: ISkill }>();
    levelSkills: ILevelSkill[];
    activeLevel: ILevel;
    activeBadge: IBadge;
    dimensions: IDimension[];
    dimensionsBySkillId: any;
    generalSkillsId: number[];

    constructor(
        private levelSkillService: LevelSkillService,
        private skillService: SkillService,
        private jhiAlertService: JhiAlertService,
        private teamService: TeamService,
        private teamsSkillService: TeamsSkillsService,
        private route: ActivatedRoute,
        private router: Router,
        private location: Location
    ) {}

    ngOnInit() {
        this.route.queryParamMap.subscribe((params: ParamMap) => {
            if (params.get('level')) {
                this.activeLevel = this.levels.find((level: ILevel) => level.id === Number.parseInt(params.get('level')));
                this.levelSkills = this.activeLevel ? this.activeLevel.skills : [];
                this.activeBadge = null;
            } else if (params.get('badge')) {
                this.activeBadge = this.badges.find((badge: IBadge) => badge.id === Number.parseInt(params.get('badge')));
                this.levelSkills = this.activeBadge ? this.activeBadge.skills : [];
                this.activeLevel = null;
            } else {
                this.levelSkillService.query().subscribe(
                    (res: HttpResponse<ILevelSkill[]>) => {
                        this.levelSkills = res.body;
                    },
                    (res: HttpErrorResponse) => this.onError(res.error)
                );
            }
        });

        this.generalSkillsId = [];
        this.dimensionsBySkillId = {};
        this.levels.forEach(level => {
            level.skills.forEach((levelSkill: ILevelSkill) => {
                const skillId = levelSkill.skillId;
                this.dimensionsBySkillId[skillId] = this.dimensionsBySkillId[skillId] || [];
                if (this.dimensionsBySkillId[skillId].indexOf(level.dimensionId) === -1) {
                    this.dimensionsBySkillId[skillId].push(level.dimensionId);
                }
            });
        });

        this.badges.forEach(badge => {
            if (badge.dimensions.length === 0) {
                this.generalSkillsId = this.generalSkillsId.concat(badge.skills.map(bs => bs.skillId));
            }

            badge.dimensions.forEach(dimension => {
                badge.skills.forEach((badgeSkill: IBadgeSkill) => {
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    getRelevantTeams(levelSkill: ILevelSkill): string {
        let relevantCount = 0;
        let completedCount = 0;
        for (const team of this.teams) {
            const skillDimensionIds = this.dimensionsBySkillId[levelSkill.skillId] || [];
            const relevant = team.participations.some(dimension => {
                return skillDimensionIds.indexOf(dimension.id) !== -1;
            });
            if (relevant) {
                relevantCount++;
                const completed = this.isSkillCompleted(team, levelSkill);
                if (completed) {
                    completedCount++;
                }
            }
        }
        if (this.generalSkillsId.indexOf(levelSkill.id) !== -1) {
            relevantCount = this.teams.length;
        }

        return `${completedCount}  / ${relevantCount}`;
    }

    private isSkillCompleted(team: ITeam, levelSkill: ILevelSkill): boolean {
        return team.skills.some((teamSkill: ITeamSkill) => {
            if (levelSkill.skillId === teamSkill.skillId) {
                return !!teamSkill.completedAt;
            }
            return false;
        });
    }

    skillClicked(event, skill: ILevelSkill) {
        const url = this.router.createUrlTree(['/overview', 'skills', skill.skillId]).toString();
        this.location.replaceState(url);
        event.preventDefault();
    }

    isActiveSkill(iLevelSkill: ILevelSkill) {
        return typeof this.activeSkill !== 'undefined' && this.activeSkill !== null && this.activeSkill.id === iLevelSkill.skillId;
    }
}
