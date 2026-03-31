import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { AppuserService } from 'app/entities/appuser/service/appuser.service';
import { IInterest } from '../interest.model';
import { InterestService } from '../service/interest.service';

import { InterestFormService } from './interest-form.service';
import { InterestUpdate } from './interest-update';

describe('Interest Management Update Component', () => {
  let comp: InterestUpdate;
  let fixture: ComponentFixture<InterestUpdate>;
  let activatedRoute: ActivatedRoute;
  let interestFormService: InterestFormService;
  let interestService: InterestService;
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

    fixture = TestBed.createComponent(InterestUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    interestFormService = TestBed.inject(InterestFormService);
    interestService = TestBed.inject(InterestService);
    appuserService = TestBed.inject(AppuserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Appuser query and add missing value', () => {
      const interest: IInterest = { id: 5876 };
      const appusers: IAppuser[] = [{ id: 14418 }];
      interest.appusers = appusers;

      const appuserCollection: IAppuser[] = [{ id: 14418 }];
      vitest.spyOn(appuserService, 'query').mockReturnValue(of(new HttpResponse({ body: appuserCollection })));
      const additionalAppusers = [...appusers];
      const expectedCollection: IAppuser[] = [...additionalAppusers, ...appuserCollection];
      vitest.spyOn(appuserService, 'addAppuserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ interest });
      comp.ngOnInit();

      expect(appuserService.query).toHaveBeenCalled();
      expect(appuserService.addAppuserToCollectionIfMissing).toHaveBeenCalledWith(
        appuserCollection,
        ...additionalAppusers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.appusersSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const interest: IInterest = { id: 5876 };
      const appuser: IAppuser = { id: 14418 };
      interest.appusers = [appuser];

      activatedRoute.data = of({ interest });
      comp.ngOnInit();

      expect(comp.appusersSharedCollection()).toContainEqual(appuser);
      expect(comp.interest).toEqual(interest);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IInterest>();
      const interest = { id: 18212 };
      vitest.spyOn(interestFormService, 'getInterest').mockReturnValue(interest);
      vitest.spyOn(interestService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(interest);
      saveSubject.complete();

      // THEN
      expect(interestFormService.getInterest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(interestService.update).toHaveBeenCalledWith(expect.objectContaining(interest));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IInterest>();
      const interest = { id: 18212 };
      vitest.spyOn(interestFormService, 'getInterest').mockReturnValue({ id: null });
      vitest.spyOn(interestService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(interest);
      saveSubject.complete();

      // THEN
      expect(interestFormService.getInterest).toHaveBeenCalled();
      expect(interestService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IInterest>();
      const interest = { id: 18212 };
      vitest.spyOn(interestService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(interestService.update).toHaveBeenCalled();
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
