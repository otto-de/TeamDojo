/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TeamdojoTestModule } from '../../../test.module';
import { LevelSkillDeleteDialogComponent } from 'app/entities/level-skill/level-skill-delete-dialog.component';
import { LevelSkillService } from 'app/entities/level-skill/level-skill.service';

describe('Component Tests', () => {
    describe('LevelSkill Management Delete Component', () => {
        let comp: LevelSkillDeleteDialogComponent;
        let fixture: ComponentFixture<LevelSkillDeleteDialogComponent>;
        let service: LevelSkillService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [LevelSkillDeleteDialogComponent],
                providers: [LevelSkillService]
            })
                .overrideTemplate(LevelSkillDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LevelSkillDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LevelSkillService);
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
