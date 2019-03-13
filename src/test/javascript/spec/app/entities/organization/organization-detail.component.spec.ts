/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { TeamdojoTestModule } from '../../../test.module';
import { OrganizationDetailComponent } from 'app/entities/organization/organization-detail.component';
import { Organization } from 'app/shared/model/organization.model';

describe('Component Tests', () => {
  describe('Organization Management Detail Component', () => {
    let comp: OrganizationDetailComponent;
    let fixture: ComponentFixture<OrganizationDetailComponent>;
    const route = ({ data: of({ organization: new Organization(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TeamdojoTestModule],
        declarations: [OrganizationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrganizationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrganizationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.organization).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
