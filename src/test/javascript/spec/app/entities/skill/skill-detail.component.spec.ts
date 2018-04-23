/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { TeamdojoTestModule } from '../../../test.module';
import { SkillDetailComponent } from 'app/entities/skill/skill-detail.component';
import { Skill } from 'app/shared/model/skill.model';

describe('Component Tests', () => {
    describe('Skill Management Detail Component', () => {
        let comp: SkillDetailComponent;
        let fixture: ComponentFixture<SkillDetailComponent>;
        const route = ({ data: of({ skill: new Skill(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [SkillDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SkillDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SkillDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.skill).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
