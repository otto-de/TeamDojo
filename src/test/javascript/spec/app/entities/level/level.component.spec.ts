/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TeamdojoTestModule } from '../../../test.module';
import { LevelComponent } from 'app/entities/level/level.component';
import { LevelService } from 'app/entities/level/level.service';
import { Level } from 'app/shared/model/level.model';

describe('Component Tests', () => {
  describe('Level Management Component', () => {
    let comp: LevelComponent;
    let fixture: ComponentFixture<LevelComponent>;
    let service: LevelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TeamdojoTestModule],
        declarations: [LevelComponent],
        providers: [LevelService]
      })
        .overrideTemplate(LevelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LevelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LevelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        Observable.of(
          new HttpResponse({
            body: [new Level(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.levels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
