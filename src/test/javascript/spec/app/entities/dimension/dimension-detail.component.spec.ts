/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { DojoTestModule } from '../../../test.module';
import { DimensionDetailComponent } from 'app/entities/dimension/dimension-detail.component';
import { Dimension } from 'app/shared/model/dimension.model';

describe('Component Tests', () => {
    describe('Dimension Management Detail Component', () => {
        let comp: DimensionDetailComponent;
        let fixture: ComponentFixture<DimensionDetailComponent>;
        const route = ({ data: of({ dimension: new Dimension(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DojoTestModule],
                declarations: [DimensionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DimensionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DimensionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dimension).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
