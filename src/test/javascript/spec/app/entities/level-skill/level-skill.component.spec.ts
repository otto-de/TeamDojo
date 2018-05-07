/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TeamdojoTestModule } from '../../../test.module';
import { LevelSkillComponent } from 'app/entities/level-skill/level-skill.component';
import { LevelSkillService } from 'app/entities/level-skill/level-skill.service';
import { LevelSkill } from 'app/shared/model/level-skill.model';

describe('Component Tests', () => {
    describe('LevelSkill Management Component', () => {
        let comp: LevelSkillComponent;
        let fixture: ComponentFixture<LevelSkillComponent>;
        let service: LevelSkillService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [LevelSkillComponent],
                providers: [LevelSkillService]
            })
                .overrideTemplate(LevelSkillComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LevelSkillComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LevelSkillService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new LevelSkill(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.levelSkills[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
