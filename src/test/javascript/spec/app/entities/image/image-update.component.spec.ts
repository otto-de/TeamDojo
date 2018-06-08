/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TeamdojoTestModule } from '../../../test.module';
import { ImageUpdateComponent } from 'app/entities/image/image-update.component';
import { ImageService } from 'app/entities/image/image.service';
import { Image } from 'app/shared/model/image.model';

describe('Component Tests', () => {
    describe('Image Management Update Component', () => {
        let comp: ImageUpdateComponent;
        let fixture: ComponentFixture<ImageUpdateComponent>;
        let service: ImageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [ImageUpdateComponent],
                providers: [ImageService]
            })
                .overrideTemplate(ImageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ImageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImageService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Image(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.image = entity;
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
                    const entity = new Image();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.image = entity;
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
