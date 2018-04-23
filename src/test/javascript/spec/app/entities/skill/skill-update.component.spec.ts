/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TeamdojoTestModule } from '../../../test.module';
import { SkillUpdateComponent } from 'app/entities/skill/skill-update.component';
import { SkillService } from 'app/entities/skill/skill.service';
import { Skill } from 'app/shared/model/skill.model';

describe('Component Tests', () => {
    describe('Skill Management Update Component', () => {
        let comp: SkillUpdateComponent;
        let fixture: ComponentFixture<SkillUpdateComponent>;
        let service: SkillService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [SkillUpdateComponent],
                providers: [SkillService]
            })
                .overrideTemplate(SkillUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SkillUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Skill(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.skill = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Skill();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.skill = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
