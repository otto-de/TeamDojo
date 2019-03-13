/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { TeamdojoTestModule } from '../../../test.module';
import { LevelSkillDetailComponent } from 'app/entities/level-skill/level-skill-detail.component';
import { LevelSkill } from 'app/shared/model/level-skill.model';

describe('Component Tests', () => {
  describe('LevelSkill Management Detail Component', () => {
    let comp: LevelSkillDetailComponent;
    let fixture: ComponentFixture<LevelSkillDetailComponent>;
    const route = ({ data: of({ levelSkill: new LevelSkill(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TeamdojoTestModule],
        declarations: [LevelSkillDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LevelSkillDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LevelSkillDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.levelSkill).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
