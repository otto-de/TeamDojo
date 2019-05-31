/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TeamdojoTestModule } from '../../../test.module';
import { TeamSkillUpdateComponent } from 'app/entities/team-skill/team-skill-update.component';
import { TeamSkillService } from 'app/entities/team-skill/team-skill.service';
import { TeamSkill } from 'app/shared/model/team-skill.model';

import { SkillService } from 'app/entities/skill';
import { TeamService } from 'app/entities/team';

describe('Component Tests', () => {
    describe('TeamSkill Management Update Component', () => {
        let comp: TeamSkillUpdateComponent;
        let fixture: ComponentFixture<TeamSkillUpdateComponent>;
        let service: TeamSkillService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [TeamSkillUpdateComponent],
                providers: [SkillService, TeamService, TeamSkillService]
            })
                .overrideTemplate(TeamSkillUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TeamSkillUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeamSkillService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TeamSkill(123);
                spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                comp.teamSkill = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TeamSkill();
                spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                comp.teamSkill = entity;
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
