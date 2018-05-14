import { Component, Input, OnInit } from '@angular/core';
import { SkillService } from 'app/entities/skill';
import { ISkill } from 'app/shared/model/skill.model';
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

@Component({
    selector: 'jhi-overview-skills',
    templateUrl: './overview-skills.component.html',
    styleUrls: ['./overview-skills.scss']
})
export class OverviewSkillsComponent {
    @Input() teams: ITeam[];
    @Input() levels: ILevel[];
    @Input() badges: IBadge[];
    skills: ISkill[];
    dimensions: IDimension[];
    dimensionsBySkillId: any;
    generalSkillsId: number[];

    constructor(
        private skillService: SkillService,
        private jhiAlertService: JhiAlertService,
        private teamService: TeamService,
        private teamsSkillService: TeamsSkillsService
    ) {}

    ngOnInit() {
        this.skillService.query().subscribe(
            (res: HttpResponse<ISkill[]>) => {
                this.skills = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.error)
        );
        this.generalSkillsId = [];
        this.dimensionsBySkillId = {}; //da sollen die Badges ebenfalls rein
        this.levels.forEach(level => {
            level.skills.forEach(skill => {
                let skillId = skill.skillId;
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
                    let skillId = badgeSkill.skillId;
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

    getRelevantTeams(skill): string {
        let relevantCount: number = 0;
        let completedCount: number = 0;
        for (let team of this.teams) {
            let skillDimensionIds = this.dimensionsBySkillId[skill.id] || [];
            const relevant = team.participations.some(dimension => {
                return skillDimensionIds.indexOf(dimension.id) !== -1;
            });
            // relevantCount = relevantCount + this.badgesWithoutDimensionId.length(); //das ist ein bisschen gecheatet
            // console.log('RELEVANT COUNT: ' + relevantCount);
            if (relevant) {
                relevantCount++;
                const completed = this.isSkillCompleted(team, skill);
                if (completed) {
                    completedCount++;
                }
            }
        }

        if (this.generalSkillsId.indexOf(skill.id) !== -1) {
            relevantCount = this.teams.length;
        }

        return `${completedCount}  / ${relevantCount}`;
    }

    //TODO Badges mit Dimension mitberücksichtigen
    //TODO Badges ohne Dimension mitberücksichtigen
    //TODO checken ob die Badges in SkillCompleted schon berücksichtigt wird
    private isSkillCompleted(team: ITeam, skill: ISkill): boolean {
        return team.skills.some((teamSkill: ITeamSkill) => {
            if (skill.id === teamSkill.skillId) {
                return !!teamSkill.completedAt; //Warum hier doppelte Negation?
            }
            return false;
        });
    }

    skillClicked(event, skill: ISkill) {
        event.preventDefault();
    }
}
