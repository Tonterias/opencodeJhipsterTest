import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { UrllinkService } from '../service/urllink.service';
import { IUrllink } from '../urllink.model';

import { UrllinkFormService } from './urllink-form.service';
import { UrllinkUpdate } from './urllink-update';

describe('Urllink Management Update Component', () => {
  let comp: UrllinkUpdate;
  let fixture: ComponentFixture<UrllinkUpdate>;
  let activatedRoute: ActivatedRoute;
  let urllinkFormService: UrllinkFormService;
  let urllinkService: UrllinkService;

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

    fixture = TestBed.createComponent(UrllinkUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    urllinkFormService = TestBed.inject(UrllinkFormService);
    urllinkService = TestBed.inject(UrllinkService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const urllink: IUrllink = { id: 20441 };

      activatedRoute.data = of({ urllink });
      comp.ngOnInit();

      expect(comp.urllink).toEqual(urllink);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IUrllink>();
      const urllink = { id: 16948 };
      vitest.spyOn(urllinkFormService, 'getUrllink').mockReturnValue(urllink);
      vitest.spyOn(urllinkService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ urllink });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(urllink);
      saveSubject.complete();

      // THEN
      expect(urllinkFormService.getUrllink).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(urllinkService.update).toHaveBeenCalledWith(expect.objectContaining(urllink));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IUrllink>();
      const urllink = { id: 16948 };
      vitest.spyOn(urllinkFormService, 'getUrllink').mockReturnValue({ id: null });
      vitest.spyOn(urllinkService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ urllink: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(urllink);
      saveSubject.complete();

      // THEN
      expect(urllinkFormService.getUrllink).toHaveBeenCalled();
      expect(urllinkService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IUrllink>();
      const urllink = { id: 16948 };
      vitest.spyOn(urllinkService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ urllink });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(urllinkService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
