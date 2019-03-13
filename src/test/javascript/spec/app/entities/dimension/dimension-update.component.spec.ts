/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TeamdojoTestModule } from '../../../test.module';
import { DimensionUpdateComponent } from 'app/entities/dimension/dimension-update.component';
import { DimensionService } from 'app/entities/dimension/dimension.service';
import { Dimension } from 'app/shared/model/dimension.model';

import { TeamService } from 'app/entities/team';
import { BadgeService } from 'app/entities/badge';

describe('Component Tests', () => {
  describe('Dimension Management Update Component', () => {
    let comp: DimensionUpdateComponent;
    let fixture: ComponentFixture<DimensionUpdateComponent>;
    let service: DimensionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TeamdojoTestModule],
        declarations: [DimensionUpdateComponent],
        providers: [TeamService, BadgeService, DimensionService]
      })
        .overrideTemplate(DimensionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DimensionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DimensionService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Dimension(123);
          spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
          comp.dimension = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Dimension();
          spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
          comp.dimension = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
