/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TeamdojoTestModule } from '../../../test.module';
import { TeamSkillDeleteDialogComponent } from 'app/entities/team-skill/team-skill-delete-dialog.component';
import { TeamSkillService } from 'app/entities/team-skill/team-skill.service';

describe('Component Tests', () => {
    describe('TeamSkill Management Delete Component', () => {
        let comp: TeamSkillDeleteDialogComponent;
        let fixture: ComponentFixture<TeamSkillDeleteDialogComponent>;
        let service: TeamSkillService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [TeamSkillDeleteDialogComponent],
                providers: [TeamSkillService]
            })
                .overrideTemplate(TeamSkillDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TeamSkillDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeamSkillService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
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
            ));
        });
    });
});
