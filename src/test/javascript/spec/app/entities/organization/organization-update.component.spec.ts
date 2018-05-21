/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TeamdojoTestModule } from '../../../test.module';
import { OrganizationUpdateComponent } from 'app/entities/organization/organization-update.component';
import { OrganizationService } from 'app/entities/organization/organization.service';
import { Organization } from 'app/shared/model/organization.model';

describe('Component Tests', () => {
    describe('Organization Management Update Component', () => {
        let comp: OrganizationUpdateComponent;
        let fixture: ComponentFixture<OrganizationUpdateComponent>;
        let service: OrganizationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TeamdojoTestModule],
                declarations: [OrganizationUpdateComponent],
                providers: [OrganizationService]
            })
                .overrideTemplate(OrganizationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrganizationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrganizationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Organization(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.organization = entity;
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
                    const entity = new Organization();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.organization = entity;
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
