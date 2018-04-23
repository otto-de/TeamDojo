/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { TeamdojoTestModule } from '../../../test.module';
import { TeamSkillDetailComponent } from 'app/entities/team-skill/team-skill-detail.component';
import { TeamSkill } from 'app/shared/model/team-skill.model';

describe('Component Tests', () => {
    describe('TeamSkill Management Detail Component', () => {
        let comp: TeamSkillDetailComponent;
        let fixture: ComponentFixture<TeamSkillDetailComponent>;
        const route = ({ data: of({ teamSkill: new TeamSkill(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [TeamSkillDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TeamSkillDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TeamSkillDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.teamSkill).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
