/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TeamdojoTestModule } from '../../../test.module';
import { DimensionDeleteDialogComponent } from 'app/entities/dimension/dimension-delete-dialog.component';
import { DimensionService } from 'app/entities/dimension/dimension.service';

describe('Component Tests', () => {
  describe('Dimension Management Delete Component', () => {
    let comp: DimensionDeleteDialogComponent;
    let fixture: ComponentFixture<DimensionDeleteDialogComponent>;
    let service: DimensionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TeamdojoTestModule],
        declarations: [DimensionDeleteDialogComponent],
        providers: [DimensionService]
      })
        .overrideTemplate(DimensionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DimensionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DimensionService);
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
