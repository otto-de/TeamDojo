/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { APP_BASE_HREF, Location, LocationStrategy, PathLocationStrategy } from '@angular/common';

import { TeamdojoTestModule } from '../../test.module';
import { TeamsSkillsComponent } from 'app/teams/teams-skills.component';
import { TeamsSkillsService } from 'app/teams/teams-skills.service';
import { Team } from 'app/shared/model/team.model';
import { SkillService } from 'app/entities/skill';
import { TeamsSelectionService } from 'app/shared/teams-selection/teams-selection.service';
import { TeamsService } from 'app/teams/teams.service';
import { TeamSkillService } from 'app/entities/team-skill';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { BreadcrumbService } from 'app/layouts/navbar/breadcrumb.service';
import { LevelService } from 'app/entities/level';
import { BadgeService } from 'app/entities/badge';
import { DimensionService } from 'app/entities/dimension';
import { Observable } from 'rxjs';
import { IAchievableSkill } from 'app/shared/model/achievable-skill.model';
import { HttpResponse } from '@angular/common/http';
import * as moment from 'moment';
import { SkillStatus } from 'app/shared/model/skill-status';

describe('Component Tests', () => {
    describe('Team Skills Component', () => {
        let comp: TeamsSkillsComponent;
        let fixture: ComponentFixture<TeamsSkillsComponent>;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [TeamsSkillsComponent],
                providers: [
                    SkillService,
                    TeamsService,
                    TeamSkillService,
                    LocalStorageService,
                    TeamsSelectionService,
                    SessionStorageService,
                    Location,
                    BreadcrumbService,
                    LevelService,
                    BadgeService,
                    DimensionService,
                    TeamsSkillsService,
                    { provide: LocationStrategy, useClass: PathLocationStrategy },
                    { provide: APP_BASE_HREF, useValue: '/' }
                ]
            })
                .overrideTemplate(TeamsSkillsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TeamsSkillsComponent);
            comp = fixture.componentInstance;
        });

        it('Should call loadAll on init', () => {
            comp.team = new Team(125);
            comp.ngOnInit();
        });

        it('Should emit an event when toggling skill relevance', done => {
            let clickedOnce = false;

            const teamsSkillsService = TestBed.get(TeamsSkillsService);
            expect(teamsSkillsService).toBeTruthy();

            spyOn(teamsSkillsService, 'updateAchievableSkill').and.callFake((teamId: number, aSkill: IAchievableSkill) => {
                expect(teamId).toEqual(125);
                expect(aSkill).toBeTruthy();
                expect(aSkill.skillId).toEqual(1100);
                expect(aSkill.title).toEqual('Input Validation');
                expect(aSkill.skillStatus).toEqual(SkillStatus.OPEN);
                expect(aSkill.irrelevant).toEqual(!clickedOnce);
                const achievableSkill = {
                    teamSkillId: 1553,
                    skillId: aSkill.skillId,
                    title: aSkill.title,
                    irrelevant: aSkill.irrelevant,
                    skillStatus: clickedOnce ? SkillStatus.OPEN : SkillStatus.IRRELEVANT
                };
                return Observable.of(new HttpResponse({ body: achievableSkill }));
            });

            const skillService = TestBed.get(SkillService);
            expect(skillService).toBeTruthy();

            spyOn(skillService, 'find').and.callFake(skillId => {
                expect(skillId).toEqual(1100);
                return Observable.of(new HttpResponse({ body: { id: skillId, title: 'Input Validation' } }));
            });

            comp.onSkillChanged.subscribe(ev => {
                expect(ev).toBeDefined();
                expect(ev.iSkill).toBeDefined();
                expect(ev.iSkill.id).toEqual(1100);
                expect(ev.iSkill.title).toEqual('Input Validation');
                expect(ev.aSkill).toBeDefined();
                expect(ev.aSkill.teamSkillId).toEqual(1553);
                expect(ev.aSkill.skillId).toEqual(1100);
                expect(ev.aSkill.title).toEqual('Input Validation');
                expect(ev.aSkill.irrelevant).toEqual(!clickedOnce);
                expect(ev.aSkill.skillStatus).toEqual(!clickedOnce ? SkillStatus.IRRELEVANT : SkillStatus.OPEN);
                if (clickedOnce) {
                    done();
                }
            });

            comp.team = new Team(125);
            comp.ngOnInit();

            const skill = { skillId: 1100, title: 'Input Validation', skillStatus: SkillStatus.OPEN, irrelevant: false };

            expect(skill['irrelevant']).toEqual(false);

            comp.toggleRelevance(skill);

            expect(skill['irrelevant']).toBeDefined();
            expect(skill['irrelevant']).toEqual(true);

            clickedOnce = true;
            comp.toggleRelevance(skill);

            expect(skill['irrelevant']).toBeDefined();
            expect(skill['irrelevant']).toEqual(false);
        });

        it('Should emit an event when clicking the team skill status', done => {
            let clickedOnce = false;

            const teamsSkillsService = TestBed.get(TeamsSkillsService);
            expect(teamsSkillsService).toBeTruthy();

            spyOn(teamsSkillsService, 'updateAchievableSkill').and.callFake((teamId: number, aSkill: IAchievableSkill) => {
                expect(teamId).toEqual(160);
                expect(aSkill).toBeTruthy();
                expect(aSkill.skillId).toEqual(1500);
                expect(aSkill.title).toEqual('Strong passwords');
                expect(aSkill.skillStatus).toEqual(clickedOnce ? SkillStatus.ACHIEVED : SkillStatus.OPEN);
                expect(aSkill.irrelevant).toEqual(false);
                const achievableSkill = {
                    teamSkillId: 1556,
                    skillId: aSkill.skillId,
                    title: aSkill.title,
                    irrelevant: aSkill.irrelevant,
                    skillStatus: clickedOnce ? SkillStatus.OPEN : SkillStatus.ACHIEVED
                };
                if (!clickedOnce) {
                    achievableSkill['achievedAt'] = moment();
                }
                return Observable.of(new HttpResponse({ body: achievableSkill }));
            });

            const skillService = TestBed.get(SkillService);
            expect(skillService).toBeTruthy();

            spyOn(skillService, 'find').and.callFake(skillId => {
                expect(skillId).toEqual(1500);
                return Observable.of(new HttpResponse({ body: { id: skillId, title: 'Strong passwords' } }));
            });

            comp.onSkillChanged.subscribe(ev => {
                expect(ev).toBeDefined();
                expect(ev.iSkill).toBeDefined();
                expect(ev.iSkill.id).toEqual(1500);
                expect(ev.iSkill.title).toEqual('Strong passwords');
                expect(ev.aSkill).toBeDefined();
                expect(ev.aSkill.teamSkillId).toEqual(1556);
                expect(ev.aSkill.skillId).toEqual(1500);
                expect(ev.aSkill.title).toEqual('Strong passwords');
                expect(ev.aSkill.irrelevant).toEqual(false);
                expect(ev.aSkill.skillStatus).toEqual(!clickedOnce ? SkillStatus.ACHIEVED : SkillStatus.OPEN);

                skill = ev.aSkill;

                if (clickedOnce) {
                    done();
                }
            });

            comp.team = new Team(160);
            comp.ngOnInit();

            let skill = { skillId: 1500, title: 'Strong passwords', skillStatus: SkillStatus.OPEN, irrelevant: false };

            expect(skill['achievedAt']).toBeUndefined();
            expect(skill['skillStatus']).toEqual(SkillStatus.OPEN);

            comp.clickSkillStatus(skill);

            expect(skill['achievedAt']).toBeDefined();
            expect(skill['skillStatus']).toEqual(SkillStatus.ACHIEVED);

            clickedOnce = true;
            comp.clickSkillStatus(skill);

            expect(skill['achievedAt']).toBeUndefined();
            expect(skill['skillStatus']).toEqual(SkillStatus.OPEN);
        });
    });
});
