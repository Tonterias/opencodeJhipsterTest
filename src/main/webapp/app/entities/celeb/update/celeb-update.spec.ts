import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { AppuserService } from 'app/entities/appuser/service/appuser.service';
import { ICeleb } from '../celeb.model';
import { CelebService } from '../service/celeb.service';

import { CelebFormService } from './celeb-form.service';
import { CelebUpdate } from './celeb-update';

describe('Celeb Management Update Component', () => {
  let comp: CelebUpdate;
  let fixture: ComponentFixture<CelebUpdate>;
  let activatedRoute: ActivatedRoute;
  let celebFormService: CelebFormService;
  let celebService: CelebService;
  let appuserService: AppuserService;

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

    fixture = TestBed.createComponent(CelebUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    celebFormService = TestBed.inject(CelebFormService);
    celebService = TestBed.inject(CelebService);
    appuserService = TestBed.inject(AppuserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Appuser query and add missing value', () => {
      const celeb: ICeleb = { id: 26548 };
      const appusers: IAppuser[] = [{ id: 14418 }];
      celeb.appusers = appusers;

      const appuserCollection: IAppuser[] = [{ id: 14418 }];
      vitest.spyOn(appuserService, 'query').mockReturnValue(of(new HttpResponse({ body: appuserCollection })));
      const additionalAppusers = [...appusers];
      const expectedCollection: IAppuser[] = [...additionalAppusers, ...appuserCollection];
      vitest.spyOn(appuserService, 'addAppuserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ celeb });
      comp.ngOnInit();

      expect(appuserService.query).toHaveBeenCalled();
      expect(appuserService.addAppuserToCollectionIfMissing).toHaveBeenCalledWith(
        appuserCollection,
        ...additionalAppusers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.appusersSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const celeb: ICeleb = { id: 26548 };
      const appuser: IAppuser = { id: 14418 };
      celeb.appusers = [appuser];

      activatedRoute.data = of({ celeb });
      comp.ngOnInit();

      expect(comp.appusersSharedCollection()).toContainEqual(appuser);
      expect(comp.celeb).toEqual(celeb);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<ICeleb>();
      const celeb = { id: 24101 };
      vitest.spyOn(celebFormService, 'getCeleb').mockReturnValue(celeb);
      vitest.spyOn(celebService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ celeb });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(celeb);
      saveSubject.complete();

      // THEN
      expect(celebFormService.getCeleb).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(celebService.update).toHaveBeenCalledWith(expect.objectContaining(celeb));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<ICeleb>();
      const celeb = { id: 24101 };
      vitest.spyOn(celebFormService, 'getCeleb').mockReturnValue({ id: null });
      vitest.spyOn(celebService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ celeb: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(celeb);
      saveSubject.complete();

      // THEN
      expect(celebFormService.getCeleb).toHaveBeenCalled();
      expect(celebService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<ICeleb>();
      const celeb = { id: 24101 };
      vitest.spyOn(celebService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ celeb });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(celebService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAppuser', () => {
      it('should forward to appuserService', () => {
        const entity = { id: 14418 };
        const entity2 = { id: 16679 };
        vitest.spyOn(appuserService, 'compareAppuser');
        comp.compareAppuser(entity, entity2);
        expect(appuserService.compareAppuser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
