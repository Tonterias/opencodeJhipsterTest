import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { AppuserService } from 'app/entities/appuser/service/appuser.service';
import { IAppphoto } from '../appphoto.model';
import { AppphotoService } from '../service/appphoto.service';

import { AppphotoFormService } from './appphoto-form.service';
import { AppphotoUpdate } from './appphoto-update';

describe('Appphoto Management Update Component', () => {
  let comp: AppphotoUpdate;
  let fixture: ComponentFixture<AppphotoUpdate>;
  let activatedRoute: ActivatedRoute;
  let appphotoFormService: AppphotoFormService;
  let appphotoService: AppphotoService;
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

    fixture = TestBed.createComponent(AppphotoUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    appphotoFormService = TestBed.inject(AppphotoFormService);
    appphotoService = TestBed.inject(AppphotoService);
    appuserService = TestBed.inject(AppuserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call appuser query and add missing value', () => {
      const appphoto: IAppphoto = { id: 30495 };
      const appuser: IAppuser = { id: 14418 };
      appphoto.appuser = appuser;

      const appuserCollection: IAppuser[] = [{ id: 14418 }];
      vitest.spyOn(appuserService, 'query').mockReturnValue(of(new HttpResponse({ body: appuserCollection })));
      const expectedCollection: IAppuser[] = [appuser, ...appuserCollection];
      vitest.spyOn(appuserService, 'addAppuserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ appphoto });
      comp.ngOnInit();

      expect(appuserService.query).toHaveBeenCalled();
      expect(appuserService.addAppuserToCollectionIfMissing).toHaveBeenCalledWith(appuserCollection, appuser);
      expect(comp.appusersCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const appphoto: IAppphoto = { id: 30495 };
      const appuser: IAppuser = { id: 14418 };
      appphoto.appuser = appuser;

      activatedRoute.data = of({ appphoto });
      comp.ngOnInit();

      expect(comp.appusersCollection()).toContainEqual(appuser);
      expect(comp.appphoto).toEqual(appphoto);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IAppphoto>();
      const appphoto = { id: 27670 };
      vitest.spyOn(appphotoFormService, 'getAppphoto').mockReturnValue(appphoto);
      vitest.spyOn(appphotoService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appphoto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(appphoto);
      saveSubject.complete();

      // THEN
      expect(appphotoFormService.getAppphoto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(appphotoService.update).toHaveBeenCalledWith(expect.objectContaining(appphoto));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IAppphoto>();
      const appphoto = { id: 27670 };
      vitest.spyOn(appphotoFormService, 'getAppphoto').mockReturnValue({ id: null });
      vitest.spyOn(appphotoService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appphoto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(appphoto);
      saveSubject.complete();

      // THEN
      expect(appphotoFormService.getAppphoto).toHaveBeenCalled();
      expect(appphotoService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IAppphoto>();
      const appphoto = { id: 27670 };
      vitest.spyOn(appphotoService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appphoto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(appphotoService.update).toHaveBeenCalled();
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
