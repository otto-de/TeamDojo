/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { TeamdojoTestModule } from '../../../test.module';
import { ReportDetailComponent } from 'app/entities/report/report-detail.component';
import { Report } from 'app/shared/model/report.model';

describe('Component Tests', () => {
  describe('Report Management Detail Component', () => {
    let comp: ReportDetailComponent;
    let fixture: ComponentFixture<ReportDetailComponent>;
    const route = ({ data: of({ report: new Report(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TeamdojoTestModule],
        declarations: [ReportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.report).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
