import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IFrontpageconfig } from '../frontpageconfig.model';
import { FrontpageconfigService } from '../service/frontpageconfig.service';

import { FrontpageconfigFormService } from './frontpageconfig-form.service';
import { FrontpageconfigUpdate } from './frontpageconfig-update';

describe('Frontpageconfig Management Update Component', () => {
  let comp: FrontpageconfigUpdate;
  let fixture: ComponentFixture<FrontpageconfigUpdate>;
  let activatedRoute: ActivatedRoute;
  let frontpageconfigFormService: FrontpageconfigFormService;
  let frontpageconfigService: FrontpageconfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideHttpClientTesting(),
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    });

    fixture = TestBed.createComponent(FrontpageconfigUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    frontpageconfigFormService = TestBed.inject(FrontpageconfigFormService);
    frontpageconfigService = TestBed.inject(FrontpageconfigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const frontpageconfig: IFrontpageconfig = { id: 24940 };

      activatedRoute.data = of({ frontpageconfig });
      comp.ngOnInit();

      expect(comp.frontpageconfig).toEqual(frontpageconfig);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IFrontpageconfig>();
      const frontpageconfig = { id: 17235 };
      vitest.spyOn(frontpageconfigFormService, 'getFrontpageconfig').mockReturnValue(frontpageconfig);
      vitest.spyOn(frontpageconfigService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ frontpageconfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(frontpageconfig);
      saveSubject.complete();

      // THEN
      expect(frontpageconfigFormService.getFrontpageconfig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(frontpageconfigService.update).toHaveBeenCalledWith(expect.objectContaining(frontpageconfig));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IFrontpageconfig>();
      const frontpageconfig = { id: 17235 };
      vitest.spyOn(frontpageconfigFormService, 'getFrontpageconfig').mockReturnValue({ id: null });
      vitest.spyOn(frontpageconfigService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ frontpageconfig: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(frontpageconfig);
      saveSubject.complete();

      // THEN
      expect(frontpageconfigFormService.getFrontpageconfig).toHaveBeenCalled();
      expect(frontpageconfigService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IFrontpageconfig>();
      const frontpageconfig = { id: 17235 };
      vitest.spyOn(frontpageconfigService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ frontpageconfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(frontpageconfigService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
