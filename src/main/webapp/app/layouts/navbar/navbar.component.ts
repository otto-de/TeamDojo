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
    ) //private teamsComponent: TeamsComponent

    {
        this.teamsSelectionService = teamsSelectionService;
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.breadcrumbService.breadcrumbChanged.subscribe(team => this.loadBreadcrumb());

        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
            this.organizationName = profileInfo.organization.name;
        });

        this.loadBreadcrumb();
    }

    loadBreadcrumb() {
        console.log('Parent childs: ', this.route.parent.children);
        this.activeLevel = null;
        this.activeBadge = null;
        this.activeDimension = null;
        this.activeTeam = null;
        this.activeSkill = null;

        /*this.route.parent.children.find( r => r.outlet === "primary").params.subscribe( value => {
            console.log("Value: ", value)
            if(value.shortName !== null && value.shortName !== 'undefined'){
                this.teamService.query({ 'shortName.equals': value.shortName }).subscribe( teams => {
                    const resultingTeams = teams.body;
                    this.activeTeam = resultingTeams.pop();
                });
            }

            if(value.skillId !== null && value.skillId !== 'undefined'){
                this.skillService.find(Number.parseInt(value.skillId)).subscribe( skill => {
                   this.activeSkill = skill.body;
                });
            }
        });


        this.route.queryParamMap.subscribe((params: ParamMap) => {
            if (params.get('dimension')) {
                this.dimensionService.find(Number.parseInt(params.get('dimension'))).subscribe( dimension => {
                    this.activeDimension = dimension.body;
                });
            }
            if (params.get('badge')) {
                this.badgeService.find(Number.parseInt(params.get('badge'))).subscribe( badge => {
                    this.activeBadge = badge.body;
                });
            }
            if (params.get('level')) {
                this.levelService.find(Number.parseInt(params.get('level'))).subscribe( level => {
                    this.activeLevel = level.body;
                });
            }
        });*/

        this.breadcrumbs = null;
        this.breadcrumbs = this.breadcrumbService.getCurrentBreadcrumb();
        console.log('Breadcrumb: ', this.breadcrumbs);
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
