import { Component, Input, OnInit } from '@angular/core';
import { SkillService } from 'app/entities/skill';
import { ISkill } from 'app/shared/model/skill.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { createRequestOption } from 'app/shared';
import { EntityArrayResponseType } from 'app/entities/team-skill/team-skill.service';
import { Observable } from 'rxjs/Observable';
import { ITeamSkill } from 'app/shared/model/team-skill.model';
//import { TeamSkillService } from 'app/entities/team-skill';
import { Team } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';
import { ITeam } from 'app/shared/model/team.model';
import { TeamsSkillsService } from 'app/teams/teams-skills.service';
import { IAchievableSkill } from 'app/shared/model/achievable-skill.model';
import { ILevel } from 'app/shared/model/level.model';

@Component({
    selector: 'jhi-overview-skills',
    templateUrl: './overview-skills.component.html',
    styleUrls: ['./overview-skills.scss']
})
export class OverviewSkillsComponent {
    @Input() teams: ITeam[];
    @Input() levels: ILevel[];
    skills: ISkill[];
    dimensionsBySkillId: any;

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
        this.dimensionsBySkillId = {}; //da sollen die BAdges rein
        this.levels.forEach(level => {
            level.skills.forEach(skill => {
                let skillId = skill.skillId;
                this.dimensionsBySkillId[skillId] = this.dimensionsBySkillId[skillId] || [];
                if (this.dimensionsBySkillId[skillId].indexOf(level.dimensionId) === -1) {
                    this.dimensionsBySkillId[skillId].push(level.dimensionId);
                }
            });
        });
        //TODO badges und Badges die an keiner Dimension hängen, sind für alle relevant
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
            if (relevant) {
                relevantCount++;
                const completed = this.isSkillCompleted(team, skill);
                if (completed) {
                    completedCount++;
                }
            }
        }
        return `${completedCount}  / ${relevantCount}`;
    }

    private isSkillCompleted(team: ITeam, skill: ISkill): boolean {
        return team.skills.some((teamSkill: ITeamSkill) => {
            if (skill.id === teamSkill.skillId) {
                return !!teamSkill.completedAt;
            }
            return false;
        });
    }

    skillClicked(event, skill: ISkill) {
        event.preventDefault();
    }
}
