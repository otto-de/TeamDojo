import { EventEmitter, Injectable, Output } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { IBadge } from 'app/shared/model/badge.model';
import { ISkill } from 'app/shared/model/skill.model';
import { ILevel } from 'app/shared/model/level.model';
import { Breadcrumb } from 'app/shared/model/breadcrumb.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Params } from '@angular/router/src/shared';

@Injectable()
export class BreadcrumbService {
    @Output() breadcrumbChanged = new EventEmitter<any>(true);

    private team: ITeam;
    private dimension: IDimension;
    private badge: IBadge;
    private skill: ISkill;
    private level: ILevel;
    private params: Params;

    constructor(private route: ActivatedRoute, private router: Router) {}

    setBreadcrumb(team: ITeam, dimension: IDimension, level: ILevel, badge: IBadge, skill: ISkill) {
        this.team = team;
        this.dimension = dimension;
        this.level = level;
        this.badge = badge;
        this.skill = skill;
        this.breadcrumbChanged.emit('Breadcrumb changed');

        this.route.queryParams.subscribe(queryParams => {
            this.params = queryParams;
        });
    }

    setBadge(badge: IBadge) {
        this.dimension = null;
        this.level = null;
        this.badge = badge;
    }

    setDimension(dimension: IDimension) {
        this.badge = null;
        this.dimension = this.dimension;
    }

    setLevel(level: ILevel) {
        this.badge = null;
        this.level = level;
    }

    setSkill(skill: ISkill) {
        this.skill = skill;
    }

    getCurrentBreadcrumb() {
        var breadcrumbs = [];

        var path = [];

        if (this.team !== null && typeof this.team !== 'undefined') {
            path.push('teams', this.team.shortName);
            const url = this.router.createUrlTree(path, { queryParams: this.params }).toString();
            breadcrumbs.push(new Breadcrumb(this.team.shortName, url, false));
        } else {
            path.push('');
        }
        if (this.dimension !== null && typeof this.dimension !== 'undefined') {
            const url = this.router.createUrlTree(path, { queryParams: this.params }).toString();
            breadcrumbs.push(new Breadcrumb(this.dimension.name, url, false));
        }
        if (this.level !== null && typeof this.level !== 'undefined') {
            const url = this.router.createUrlTree(path, { queryParams: this.params }).toString();
            breadcrumbs.push(new Breadcrumb(this.level.name, url, false));
        }
        if (this.badge !== null && typeof this.badge !== 'undefined') {
            const url = this.router.createUrlTree(path, { queryParams: this.params }).toString();
            breadcrumbs.push(new Breadcrumb(this.badge.name, url, false));
        }
        if (this.skill !== null && typeof this.skill !== 'undefined') {
            path.push('skills', this.skill.id);
            const url = this.router.createUrlTree(path, { queryParams: this.params }).toString();
            breadcrumbs.push(new Breadcrumb(this.skill.title, url, false));
        }
        if (breadcrumbs.length > 0) {
            breadcrumbs[breadcrumbs.length - 1].active = true;
        }
        return breadcrumbs;
    }
}
