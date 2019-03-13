import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { LevelSkill } from 'app/shared/model/level-skill.model';
import { LevelSkillService } from './level-skill.service';
import { LevelSkillComponent } from './level-skill.component';
import { LevelSkillDetailComponent } from './level-skill-detail.component';
import { LevelSkillUpdateComponent } from './level-skill-update.component';
import { LevelSkillDeletePopupComponent } from './level-skill-delete-dialog.component';

@Injectable()
export class LevelSkillResolve implements Resolve<any> {
  constructor(private service: LevelSkillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id);
    }
    return new LevelSkill();
  }
}

export const levelSkillRoute: Routes = [
  {
    path: 'level-skill',
    component: LevelSkillComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.levelSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'level-skill/:id/view',
    component: LevelSkillDetailComponent,
    resolve: {
      levelSkill: LevelSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.levelSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'level-skill/new',
    component: LevelSkillUpdateComponent,
    resolve: {
      levelSkill: LevelSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.levelSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'level-skill/:id/edit',
    component: LevelSkillUpdateComponent,
    resolve: {
      levelSkill: LevelSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.levelSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const levelSkillPopupRoute: Routes = [
  {
    path: 'level-skill/:id/delete',
    component: LevelSkillDeletePopupComponent,
    resolve: {
      levelSkill: LevelSkillResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'teamdojoApp.levelSkill.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
