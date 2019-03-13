/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TeamdojoTestModule } from '../../../test.module';
import { BadgeSkillDeleteDialogComponent } from 'app/entities/badge-skill/badge-skill-delete-dialog.component';
import { BadgeSkillService } from 'app/entities/badge-skill/badge-skill.service';

describe('Component Tests', () => {
  describe('BadgeSkill Management Delete Component', () => {
    let comp: BadgeSkillDeleteDialogComponent;
    let fixture: ComponentFixture<BadgeSkillDeleteDialogComponent>;
    let service: BadgeSkillService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TeamdojoTestModule],
        declarations: [BadgeSkillDeleteDialogComponent],
        providers: [BadgeSkillService]
      })
        .overrideTemplate(BadgeSkillDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BadgeSkillDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BadgeSkillService);
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
