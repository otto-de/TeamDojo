/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamdojoTestModule } from '../../test.module';
import { TeamsAchievementsComponent } from 'app/teams/teams-achievements.component';
import { TeamsAchievementsService } from 'app/teams/teams-achievements.service';
import { Badge } from 'app/shared/model/badge.model';
import { Level } from 'app/shared/model/level.model';
import { Team } from 'app/shared/model/team.model';
import { Dimension } from 'app/shared/model/dimension.model';
import Util from '../../helpers/Util.service';

describe('Component Tests', () => {
    describe('Team Achievements Component', () => {
        let comp: TeamsAchievementsComponent;
        let fixture: ComponentFixture<TeamsAchievementsComponent>;
        const buildEntity = Util.wrap;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [TeamsAchievementsComponent],
                providers: [TeamsAchievementsService]
            })
                .overrideTemplate(TeamsAchievementsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TeamsAchievementsComponent);
            comp = fixture.componentInstance;
        });

        it('Should call load all on init', () => {
            // GIVEN
            const entity = new Team(122);
            comp.team = entity;
            comp.badges = [new Badge(123)];

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(comp.badges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('Should load levels depending on team participations', () => {
            // GIVEN
            const entity = new Team(121);
            entity.participations = [new Dimension(122)];
            comp.team = entity;
            comp.badges = [];
            comp.team.participations[0].levels = [
                buildEntity(new Level(123), { dimensionId: 122 }),
                buildEntity(new Level(124), { dimensionId: 122 })
            ];

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(comp.team.participations[0].id).toEqual(122);
            expect(comp.team.participations[0].levels.length).toEqual(2);
            expect(comp.team.participations[0].levels[0].id).toEqual(123);
            expect(comp.team.participations[0].levels[1].id).toEqual(124);
        });

        it('Should not fail if no level exists for dimension', () => {
            // GIVEN
            const entity = new Team(121);
            entity.participations = [new Dimension(122)];
            comp.team = entity;
            comp.team.participations[0].levels = [];
            comp.badges = [];
            // WHEN
            comp.ngOnInit();

            // THEN
            expect(comp.team.participations[0].id).toEqual(122);
            expect(comp.team.participations[0].levels.length).toEqual(0);
        });

        it('Should not fail if only a single level is retrieved', () => {
            // GIVEN
            const entity = new Team(121);
            entity.participations = [new Dimension(122)];
            comp.team = entity;
            comp.team.participations[0].levels = [buildEntity(new Level(123), { dimensionId: 122 })];
            comp.badges = [];
            // WHEN
            comp.ngOnInit();

            // THEN
            expect(comp.team.participations[0].id).toEqual(122);
            expect(comp.team.participations[0].levels.length).toEqual(1);
            expect(comp.team.participations[0].levels[0].id).toEqual(123);
        });

        it('Should load multiple dimensions with levels', () => {
            // GIVEN
            const entity = new Team(121);
            entity.participations = [new Dimension(122), new Dimension(123)];
            comp.team = entity;
            comp.team.participations[0].levels = [buildEntity(new Level(124), { dimensionId: 122 })];
            comp.team.participations[1].levels = [buildEntity(new Level(125), { dimensionId: 123 })];
            comp.badges = [];
            // WHEN
            comp.ngOnInit();

            // THEN
            expect(comp.team.participations[0].id).toEqual(122);
            expect(comp.team.participations[0].levels.length).toEqual(1);
            expect(comp.team.participations[0].levels[0].id).toEqual(124);

            expect(comp.team.participations[1].id).toEqual(123);
            expect(comp.team.participations[1].levels.length).toEqual(1);
            expect(comp.team.participations[1].levels[0].id).toEqual(125);
        });
    });
});
