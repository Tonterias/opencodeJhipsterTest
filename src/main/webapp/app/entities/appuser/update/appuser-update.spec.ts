import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IActivity } from 'app/entities/activity/activity.model';
import { ActivityService } from 'app/entities/activity/service/activity.service';
import { ICeleb } from 'app/entities/celeb/celeb.model';
import { CelebService } from 'app/entities/celeb/service/celeb.service';
import { IInterest } from 'app/entities/interest/interest.model';
import { InterestService } from 'app/entities/interest/service/interest.service';
import { UserService } from 'app/entities/user/service/user.service';
import { IUser } from 'app/entities/user/user.model';
import { IAppuser } from '../appuser.model';
import { AppuserService } from '../service/appuser.service';

import { AppuserFormService } from './appuser-form.service';
import { AppuserUpdate } from './appuser-update';

describe('Appuser Management Update Component', () => {
  let comp: AppuserUpdate;
  let fixture: ComponentFixture<AppuserUpdate>;
  let activatedRoute: ActivatedRoute;
  let appuserFormService: AppuserFormService;
  let appuserService: AppuserService;
  let userService: UserService;
  let interestService: InterestService;
  let activityService: ActivityService;
  let celebService: CelebService;

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

    fixture = TestBed.createComponent(AppuserUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    appuserFormService = TestBed.inject(AppuserFormService);
    appuserService = TestBed.inject(AppuserService);
    userService = TestBed.inject(UserService);
    interestService = TestBed.inject(InterestService);
    activityService = TestBed.inject(ActivityService);
    celebService = TestBed.inject(CelebService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call User query and add missing value', () => {
      const appuser: IAppuser = { id: 16679 };
      const user: IUser = { id: 3944 };
      appuser.user = user;

      const userCollection: IUser[] = [{ id: 3944 }];
      vitest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      vitest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ appuser });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.usersSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Interest query and add missing value', () => {
      const appuser: IAppuser = { id: 16679 };
      const interests: IInterest[] = [{ id: 18212 }];
      appuser.interests = interests;

      const interestCollection: IInterest[] = [{ id: 18212 }];
      vitest.spyOn(interestService, 'query').mockReturnValue(of(new HttpResponse({ body: interestCollection })));
      const additionalInterests = [...interests];
      const expectedCollection: IInterest[] = [...additionalInterests, ...interestCollection];
      vitest.spyOn(interestService, 'addInterestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ appuser });
      comp.ngOnInit();

      expect(interestService.query).toHaveBeenCalled();
      expect(interestService.addInterestToCollectionIfMissing).toHaveBeenCalledWith(
        interestCollection,
        ...additionalInterests.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.interestsSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Activity query and add missing value', () => {
      const appuser: IAppuser = { id: 16679 };
      const activities: IActivity[] = [{ id: 3219 }];
      appuser.activities = activities;

      const activityCollection: IActivity[] = [{ id: 3219 }];
      vitest.spyOn(activityService, 'query').mockReturnValue(of(new HttpResponse({ body: activityCollection })));
      const additionalActivities = [...activities];
      const expectedCollection: IActivity[] = [...additionalActivities, ...activityCollection];
      vitest.spyOn(activityService, 'addActivityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ appuser });
      comp.ngOnInit();

      expect(activityService.query).toHaveBeenCalled();
      expect(activityService.addActivityToCollectionIfMissing).toHaveBeenCalledWith(
        activityCollection,
        ...additionalActivities.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.activitiesSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Celeb query and add missing value', () => {
      const appuser: IAppuser = { id: 16679 };
      const celebs: ICeleb[] = [{ id: 24101 }];
      appuser.celebs = celebs;

      const celebCollection: ICeleb[] = [{ id: 24101 }];
      vitest.spyOn(celebService, 'query').mockReturnValue(of(new HttpResponse({ body: celebCollection })));
      const additionalCelebs = [...celebs];
      const expectedCollection: ICeleb[] = [...additionalCelebs, ...celebCollection];
      vitest.spyOn(celebService, 'addCelebToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ appuser });
      comp.ngOnInit();

      expect(celebService.query).toHaveBeenCalled();
      expect(celebService.addCelebToCollectionIfMissing).toHaveBeenCalledWith(
        celebCollection,
        ...additionalCelebs.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.celebsSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const appuser: IAppuser = { id: 16679 };
      const user: IUser = { id: 3944 };
      appuser.user = user;
      const interest: IInterest = { id: 18212 };
      appuser.interests = [interest];
      const activity: IActivity = { id: 3219 };
      appuser.activities = [activity];
      const celeb: ICeleb = { id: 24101 };
      appuser.celebs = [celeb];

      activatedRoute.data = of({ appuser });
      comp.ngOnInit();

      expect(comp.usersSharedCollection()).toContainEqual(user);
      expect(comp.interestsSharedCollection()).toContainEqual(interest);
      expect(comp.activitiesSharedCollection()).toContainEqual(activity);
      expect(comp.celebsSharedCollection()).toContainEqual(celeb);
      expect(comp.appuser).toEqual(appuser);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IAppuser>();
      const appuser = { id: 14418 };
      vitest.spyOn(appuserFormService, 'getAppuser').mockReturnValue(appuser);
      vitest.spyOn(appuserService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appuser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(appuser);
      saveSubject.complete();

      // THEN
      expect(appuserFormService.getAppuser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(appuserService.update).toHaveBeenCalledWith(expect.objectContaining(appuser));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IAppuser>();
      const appuser = { id: 14418 };
      vitest.spyOn(appuserFormService, 'getAppuser').mockReturnValue({ id: null });
      vitest.spyOn(appuserService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appuser: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(appuser);
      saveSubject.complete();

      // THEN
      expect(appuserFormService.getAppuser).toHaveBeenCalled();
      expect(appuserService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IAppuser>();
      const appuser = { id: 14418 };
      vitest.spyOn(appuserService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appuser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(appuserService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('should forward to userService', () => {
        const entity = { id: 3944 };
        const entity2 = { id: 6275 };
        vitest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareInterest', () => {
      it('should forward to interestService', () => {
        const entity = { id: 18212 };
        const entity2 = { id: 5876 };
        vitest.spyOn(interestService, 'compareInterest');
        comp.compareInterest(entity, entity2);
        expect(interestService.compareInterest).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareActivity', () => {
      it('should forward to activityService', () => {
        const entity = { id: 3219 };
        const entity2 = { id: 30644 };
        vitest.spyOn(activityService, 'compareActivity');
        comp.compareActivity(entity, entity2);
        expect(activityService.compareActivity).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCeleb', () => {
      it('should forward to celebService', () => {
        const entity = { id: 24101 };
        const entity2 = { id: 26548 };
        vitest.spyOn(celebService, 'compareCeleb');
        comp.compareCeleb(entity, entity2);
        expect(celebService.compareCeleb).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
