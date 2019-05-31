/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TeamdojoTestModule } from '../../../test.module';
import { TeamUpdateComponent } from 'app/entities/team/team-update.component';
import { TeamService } from 'app/entities/team/team.service';
import { Team } from 'app/shared/model/team.model';

import { DimensionService } from 'app/entities/dimension';
import { ImageService } from 'app/entities/image';

describe('Component Tests', () => {
    describe('Team Management Update Component', () => {
        let comp: TeamUpdateComponent;
        let fixture: ComponentFixture<TeamUpdateComponent>;
        let service: TeamService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [TeamUpdateComponent],
                providers: [DimensionService, ImageService, TeamService]
            })
                .overrideTemplate(TeamUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TeamUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeamService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Team(123);
                spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                comp.team = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Team();
                spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                comp.team = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
