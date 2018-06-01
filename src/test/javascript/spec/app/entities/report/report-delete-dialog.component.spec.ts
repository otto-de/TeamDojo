/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TeamdojoTestModule } from '../../../test.module';
import { ReportDeleteDialogComponent } from 'app/entities/report/report-delete-dialog.component';
import { ReportService } from 'app/entities/report/report.service';

describe('Component Tests', () => {
    describe('Report Management Delete Component', () => {
        let comp: ReportDeleteDialogComponent;
        let fixture: ComponentFixture<ReportDeleteDialogComponent>;
        let service: ReportService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [ReportDeleteDialogComponent],
                providers: [ReportService]
            })
                .overrideTemplate(ReportDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReportDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReportService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
