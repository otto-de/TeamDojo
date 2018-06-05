import { ActivatedRouteSnapshot, Resolve, Route, Router, RouterStateSnapshot } from '@angular/router';

import { TeamsComponent } from './';
import { TeamsService } from 'app/teams/teams.service';
import { Team } from 'app/shared/model/team.model';
import { Injectable } from '@angular/core';
import { UserRouteAccessService } from 'app/core';
import { SkillDetailsComponent } from 'app/teams/skill-details/skill-details.component';
import { TeamSkillService } from 'app/entities/team-skill';
import { TeamsSelectionResolve } from 'app/shared/teams-selection/teams-selection.resolve';
import {
    AllBadgeSkillsResolve,
    AllBadgesResolve,
    AllCommentsResolve,
    AllLevelSkillsResolve,
    AllLevelsResolve,
    AllSkillsResolve,
    AllTeamsResolve,
    SkillResolve
} from 'app/shared/common.resolver';

@Injectable()
export class TeamAndTeamSkillResolve implements Resolve<any> {
    constructor(private teamService: TeamsService, private teamSkillService: TeamSkillService, private router: Router) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const shortName = route.params['shortName'] ? route.params['shortName'] : null;
        if (shortName) {
            return this.teamService
                .query({
                    'shortName.equals': shortName
                })
                .flatMap(teamResponse => {
                    if (teamResponse.body.length === 0) {
                        this.router.navigate(['/error']);
                    }
                    const team = teamResponse.body[0];
                    return this.teamSkillService.query({ 'teamId.equals': team.id }).map(teamSkillResponse => {
                        team.skills = teamSkillResponse.body;
                        return team;
                    });
                });
        }
        return new Team();
    }
}

export const TEAMS_ROUTES: Route[] = [
    {
        path: 'teams/:shortName',
        component: TeamsComponent,
        resolve: {
            team: TeamAndTeamSkillResolve,
            levels: AllLevelsResolve,
            badges: AllBadgesResolve,
            levelSkills: AllLevelSkillsResolve,
            badgeSkills: AllBadgeSkillsResolve,
            skills: AllSkillsResolve
        },
        data: {
            authorities: [],
            pageTitle: 'teamdojoApp.teams.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'teams/:shortName/skills/:skillId',
        component: SkillDetailsComponent,
        resolve: {
            team: TeamAndTeamSkillResolve,
            skill: SkillResolve,
            skills: AllSkillsResolve,
            comments: AllCommentsResolve,
            teams: AllTeamsResolve,
            selectedTeam: TeamsSelectionResolve,
            levels: AllLevelsResolve,
            badges: AllBadgesResolve,
            levelSkills: AllLevelSkillsResolve,
            badgeSkills: AllBadgeSkillsResolve
        },
        data: {
            authorities: [],
            pageTitle: 'teamdojoApp.teams.skills.title'
        }
    }
];
