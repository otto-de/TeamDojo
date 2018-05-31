import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Comment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { CommentComponent } from './comment.component';
import { CommentDetailComponent } from './comment-detail.component';
import { CommentUpdateComponent } from './comment-update.component';
import { CommentDeletePopupComponent } from './comment-delete-dialog.component';

@Injectable()
export class CommentResolve implements Resolve<any> {
    constructor(private service: CommentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Comment();
    }
}

export const commentRoute: Routes = [
    {
        path: 'comment',
        component: CommentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.comment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comment/:id/view',
        component: CommentDetailComponent,
        resolve: {
            comment: CommentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.comment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comment/new',
        component: CommentUpdateComponent,
        resolve: {
            comment: CommentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.comment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comment/:id/edit',
        component: CommentUpdateComponent,
        resolve: {
            comment: CommentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.comment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commentPopupRoute: Routes = [
    {
        path: 'comment/:id/delete',
        component: CommentDeletePopupComponent,
        resolve: {
            comment: CommentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'teamdojoApp.comment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
