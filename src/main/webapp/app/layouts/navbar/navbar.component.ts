import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, ParamMap, Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { JhiLanguageHelper, Principal, LoginModalService, LoginService } from 'app/core';
import { ProfileService } from '../profiles/profile.service';
import { TeamsSelectionService } from '../../teams/teams-selection/teams-selection.service';
import { TeamsSelectionComponent } from 'app/teams/teams-selection/teams-selection.component';
import { ITeam, Team } from 'app/shared/model/team.model';
import { IBadge } from 'app/shared/model/badge.model';
import { ILevel } from 'app/shared/model/level.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { TeamService } from 'app/entities/team';
import { DimensionService } from 'app/entities/dimension';
import { BadgeService } from 'app/entities/badge';
import { LevelService } from 'app/entities/level';
import { TeamsComponent } from 'app/teams';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill';
import { BreadcrumbService } from 'app/layouts/navbar/breadcrumb.service';
import { IBreadcrumb } from 'app/shared/model/breadcrumb.model';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    organizationName: string;
    modalRef: NgbModalRef;
    isTeamSelectionOpen = false;
    teamsSelectionService: TeamsSelectionService;

    activeLevel: ILevel;
    activeBadge: IBadge;
    activeDimension: IDimension;
    activeTeam: ITeam;
    activeSkill: ISkill;
    breadcrumbs: IBreadcrumb[];

    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private principal: Principal,
        private loginModalService: LoginModalService,
        teamsSelectionService: TeamsSelectionService,
        private profileService: ProfileService,
        private modalService: NgbModal,
        private router: Router,
        private route: ActivatedRoute,
        private teamService: TeamService,
        private dimensionService: DimensionService,
        private badgeService: BadgeService,
        private levelService: LevelService,
        private skillService: SkillService,
        private breadcrumbService: BreadcrumbService
    ) {
        this.teamsSelectionService = teamsSelectionService;
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
        this.breadcrumbService.breadcrumbChanged.subscribe(breadcrumb => {
            this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
        });

        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
            this.organizationName = profileInfo.organization.name;
        });
    }

    ngAfterViewInit() {}

    loadBreadcrumb() {
        this.activeLevel = null;
        this.activeBadge = null;
        this.activeDimension = null;
        this.activeTeam = null;
        this.activeSkill = null;
        this.breadcrumbs = null;
        this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
    }

    changeLanguage(languageKey: string) {
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }

    selectTeam(): NgbModalRef {
        if (this.isTeamSelectionOpen) {
            return;
        }
        this.isTeamSelectionOpen = true;
        const modalRef = this.modalService.open(TeamsSelectionComponent, { size: 'lg' });
        modalRef.result.then(
            result => {
                this.isTeamSelectionOpen = false;
            },
            reason => {
                this.isTeamSelectionOpen = false;
            }
        );
        return modalRef;
    }

    isTeamOverview() {
        return this.activeTeam !== null && this.activeTeam !== 'undefined';
    }

    isSkillDetail() {
        return this.activeSkill !== null && this.activeSkill !== 'undfined';
    }

    getTeamImage(team: Team) {
        return team.picture && team.pictureContentType ? `data:${team.pictureContentType};base64,${team.picture}` : null;
    }
}
