import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { JhiLanguageHelper, Principal, LoginModalService, LoginService } from 'app/core';
import { ProfileService } from '../profiles/profile.service';
import { TeamsSelectionService } from '../../teams/teams-selection/teams-selection.service';
import { TeamsSelectionComponent } from 'app/teams/teams-selection/teams-selection.component';
import { Team } from 'app/shared/model/team.model';

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
    modalRef: NgbModalRef;
    isTeamSelectionOpen = false;
    teamsSelectionService: TeamsSelectionService;

    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private principal: Principal,
        private loginModalService: LoginModalService,
        teamsSelectionService: TeamsSelectionService,
        private profileService: ProfileService,
        private modalService: NgbModal,
        private router: Router
    ) {
        this.teamsSelectionService = teamsSelectionService;
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
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

    getTeamImage(team: Team) {
        return team.picture && team.pictureContentType ? `data:${team.pictureContentType};base64,${team.picture}` : null;
    }
}
