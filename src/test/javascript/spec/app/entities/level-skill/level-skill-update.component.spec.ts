/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { DojoTestModule } from '../../../test.module';
import { LevelSkillUpdateComponent } from 'app/entities/level-skill/level-skill-update.component';
import { LevelSkillService } from 'app/entities/level-skill/level-skill.service';
import { LevelSkill } from 'app/shared/model/level-skill.model';

import { SkillService } from 'app/entities/skill';
import { LevelService } from 'app/entities/level';

describe('Component Tests', () => {
    describe('LevelSkill Management Update Component', () => {
        let comp: LevelSkillUpdateComponent;
        let fixture: ComponentFixture<LevelSkillUpdateComponent>;
        let service: LevelSkillService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DojoTestModule],
                declarations: [LevelSkillUpdateComponent],
                providers: [SkillService, LevelService, LevelSkillService]
            })
                .overrideTemplate(LevelSkillUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LevelSkillUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LevelSkillService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LevelSkill(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.levelSkill = entity;
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
                    const entity = new LevelSkill();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.levelSkill = entity;
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
