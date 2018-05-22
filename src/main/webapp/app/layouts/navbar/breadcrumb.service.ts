import { EventEmitter, Injectable, Output } from '@angular/core';
import { ITeam, Team } from 'app/shared/model/team.model';
import { TeamsService } from 'app/teams/teams.service';
import { LocalStorageService } from 'ngx-webstorage';
import { IDimension } from 'app/shared/model/dimension.model';
import { IBadge } from 'app/shared/model/badge.model';
import { ISkill } from 'app/shared/model/skill.model';
import { ILevel } from 'app/shared/model/level.model';
import { IAchievableSkill } from 'app/shared/model/achievable-skill.model';
import { Breadcrumb, IBreadcrumb } from 'app/shared/model/breadcrumb.model';

@Injectable()
export class BreadcrumbService {
    @Output() breadcrumbChanged = new EventEmitter<any>();

    private team: ITeam;
    private dimension: IDimension;
    private badge: IBadge;
    private skill: ISkill;
    private level: ILevel;
    private breadcrumbs: IBreadcrumb[];

    constructor() {}

    setBreadcrumb(team: ITeam, dimension: IDimension, level: ILevel, badge: IBadge, skill: ISkill) {
        this.team = team;
        this.dimension = dimension;
        this.level = level;
        this.badge = badge;
        this.skill = skill;
        this.breadcrumbChanged.emit('Breadcrumb changed');
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
        this.breadcrumbs = [];
        if (this.team !== null && typeof this.team !== 'undefined') {
            this.breadcrumbs.push(new Breadcrumb(this.team.shortName, '', false));
        }
        if (this.dimension !== null && typeof this.dimension !== 'undefined') {
            this.breadcrumbs.push(new Breadcrumb(this.dimension.name, '', false));
        }
        if (this.level !== null && typeof this.level !== 'undefined') {
            this.breadcrumbs.push(new Breadcrumb(this.level.name, '', false));
        }
        if (this.badge !== null && typeof this.badge !== 'undefined') {
            this.breadcrumbs.push(new Breadcrumb(this.badge.name, '', false));
        }
        if (this.skill !== null && typeof this.skill !== 'undefined') {
            this.breadcrumbs.push(new Breadcrumb(this.skill.title, '', false));
        }
        if (this.breadcrumbs.length > 0) {
            this.breadcrumbs[this.breadcrumbs.length - 1].active = true;
        }
        return this.breadcrumbs;
    }
}
