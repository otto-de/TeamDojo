/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { TeamdojoTestModule } from '../../../test.module';
import { BadgeDetailComponent } from 'app/entities/badge/badge-detail.component';
import { Badge } from 'app/shared/model/badge.model';

describe('Component Tests', () => {
  describe('Badge Management Detail Component', () => {
    let comp: BadgeDetailComponent;
    let fixture: ComponentFixture<BadgeDetailComponent>;
    const route = ({ data: of({ badge: new Badge(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TeamdojoTestModule],
        declarations: [BadgeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BadgeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BadgeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.badge).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
