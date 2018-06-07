/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TeamdojoTestModule } from '../../../test.module';
import { BadgeUpdateComponent } from 'app/entities/badge/badge-update.component';
import { BadgeService } from 'app/entities/badge/badge.service';
import { Badge } from 'app/shared/model/badge.model';

import { DimensionService } from 'app/entities/dimension';
import { ImageService } from 'app/entities/image';

describe('Component Tests', () => {
    describe('Badge Management Update Component', () => {
        let comp: BadgeUpdateComponent;
        let fixture: ComponentFixture<BadgeUpdateComponent>;
        let service: BadgeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [BadgeUpdateComponent],
                providers: [DimensionService, ImageService, BadgeService]
            })
                .overrideTemplate(BadgeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BadgeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BadgeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Badge(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.badge = entity;
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
                    const entity = new Badge();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.badge = entity;
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
