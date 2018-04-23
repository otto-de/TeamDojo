import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { TeamSkill } from 'app/shared/model/team-skill.model';
import { TeamSkillService } from './team-skill.service';
import { TeamSkillComponent } from './team-skill.component';
import { TeamSkillDetailComponent } from './team-skill-detail.component';
import { TeamSkillUpdateComponent } from './team-skill-update.component';
import { TeamSkillDeletePopupComponent } from './team-skill-delete-dialog.component';

@Injectable()
export class TeamSkillResolve implements Resolve<any> {
    constructor(private service: TeamSkillService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new TeamSkill();
    }
}

export const teamSkillRoute: Routes = [
    {
        path: 'team-skill',
        component: TeamSkillComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.teamSkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'team-skill/:id/view',
        component: TeamSkillDetailComponent,
        resolve: {
            teamSkill: TeamSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.teamSkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'team-skill/new',
        component: TeamSkillUpdateComponent,
        resolve: {
            teamSkill: TeamSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.teamSkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'team-skill/:id/edit',
        component: TeamSkillUpdateComponent,
        resolve: {
            teamSkill: TeamSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.teamSkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const teamSkillPopupRoute: Routes = [
    {
        path: 'team-skill/:id/delete',
        component: TeamSkillDeletePopupComponent,
        resolve: {
            teamSkill: TeamSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.teamSkill.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
