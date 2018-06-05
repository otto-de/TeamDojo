import { Route } from '@angular/router';
import { OverviewComponent } from './';
import { OverviewSkillDetailsComponent } from 'app/overview/skills/skill-details/overview-skill-details.component';
import { TeamsSelectionResolve } from 'app/shared/teams-selection/teams-selection.resolve';
import { AllCommentsResolve, AllSkillsResolve, DojoModelResolve, SkillResolve } from 'app/shared/common.resolver';

export const OVERVIEW_ROUTE: Route[] = [
    {
        path: '',
        component: OverviewComponent,
        data: {
            authorities: [],
            pageTitle: 'teamdojoApp.teams.home.title'
        },
        resolve: {
            dojoModel: DojoModelResolve,
            skills: AllSkillsResolve,
            selectedTeam: TeamsSelectionResolve
        }
    },
    {
        path: 'overview/skills/:skillId',
        component: OverviewSkillDetailsComponent,
        resolve: {
            dojoModel: DojoModelResolve,
            skill: SkillResolve,
            comments: AllCommentsResolve,
            selectedTeam: TeamsSelectionResolve,
            skills: AllSkillsResolve
        },
        data: {
            authorities: [],
            pageTitle: 'teamdojoApp.teams.skills.title'
        }
    }
];
