/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { DojoTestModule } from '../../../test.module';
import { BadgeSkillDetailComponent } from 'app/entities/badge-skill/badge-skill-detail.component';
import { BadgeSkill } from 'app/shared/model/badge-skill.model';

describe('Component Tests', () => {
    describe('BadgeSkill Management Detail Component', () => {
        let comp: BadgeSkillDetailComponent;
        let fixture: ComponentFixture<BadgeSkillDetailComponent>;
        const route = ({ data: of({ badgeSkill: new BadgeSkill(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DojoTestModule],
                declarations: [BadgeSkillDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BadgeSkillDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BadgeSkillDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.badgeSkill).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
