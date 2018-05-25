/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TeamdojoTestModule } from '../../../test.module';
import { ReportComponent } from 'app/entities/report/report.component';
import { ReportService } from 'app/entities/report/report.service';
import { Report } from 'app/shared/model/report.model';

describe('Component Tests', () => {
    describe('Report Management Component', () => {
        let comp: ReportComponent;
        let fixture: ComponentFixture<ReportComponent>;
        let service: ReportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [ReportComponent],
                providers: [ReportService]
            })
                .overrideTemplate(ReportComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReportComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReportService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new Report(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.reports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
