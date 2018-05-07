/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TeamdojoTestModule } from '../../../test.module';
import { TeamDeleteDialogComponent } from 'app/entities/team/team-delete-dialog.component';
import { TeamService } from 'app/entities/team/team.service';

describe('Component Tests', () => {
    describe('Team Management Delete Component', () => {
        let comp: TeamDeleteDialogComponent;
        let fixture: ComponentFixture<TeamDeleteDialogComponent>;
        let service: TeamService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [TeamDeleteDialogComponent],
                providers: [TeamService]
            })
                .overrideTemplate(TeamDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TeamDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeamService);
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
