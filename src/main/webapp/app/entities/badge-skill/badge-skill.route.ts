import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { BadgeSkill } from 'app/shared/model/badge-skill.model';
import { BadgeSkillService } from './badge-skill.service';
import { BadgeSkillComponent } from './badge-skill.component';
import { BadgeSkillDetailComponent } from './badge-skill-detail.component';
import { BadgeSkillUpdateComponent } from './badge-skill-update.component';
import { BadgeSkillDeletePopupComponent } from './badge-skill-delete-dialog.component';

@Injectable()
export class BadgeSkillResolve implements Resolve<any> {
  constructor(private service: BadgeSkillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id);
    }
    return new BadgeSkill();
  }
}

export const badgeSkillRoute: Routes = [
  {
    path: 'badge-skill',
    component: BadgeSkillComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.badgeSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'badge-skill/:id/view',
    component: BadgeSkillDetailComponent,
    resolve: {
      badgeSkill: BadgeSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.badgeSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'badge-skill/new',
    component: BadgeSkillUpdateComponent,
    resolve: {
      badgeSkill: BadgeSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.badgeSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'badge-skill/:id/edit',
    component: BadgeSkillUpdateComponent,
    resolve: {
      badgeSkill: BadgeSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.badgeSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const badgeSkillPopupRoute: Routes = [
  {
    path: 'badge-skill/:id/delete',
    component: BadgeSkillDeletePopupComponent,
    resolve: {
      badgeSkill: BadgeSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.badgeSkill.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
