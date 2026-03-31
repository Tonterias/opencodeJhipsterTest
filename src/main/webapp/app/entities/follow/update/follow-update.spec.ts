import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { AppuserService } from 'app/entities/appuser/service/appuser.service';
import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';
import { IFollow } from '../follow.model';
import { FollowService } from '../service/follow.service';

import { FollowFormService } from './follow-form.service';
import { FollowUpdate } from './follow-update';

describe('Follow Management Update Component', () => {
  let comp: FollowUpdate;
  let fixture: ComponentFixture<FollowUpdate>;
  let activatedRoute: ActivatedRoute;
  let followFormService: FollowFormService;
  let followService: FollowService;
  let appuserService: AppuserService;
  let communityService: CommunityService;

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

    fixture = TestBed.createComponent(FollowUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    followFormService = TestBed.inject(FollowFormService);
    followService = TestBed.inject(FollowService);
    appuserService = TestBed.inject(AppuserService);
    communityService = TestBed.inject(CommunityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Appuser query and add missing value', () => {
      const follow: IFollow = { id: 10700 };
      const followed: IAppuser = { id: 14418 };
      follow.followed = followed;
      const following: IAppuser = { id: 14418 };
      follow.following = following;

      const appuserCollection: IAppuser[] = [{ id: 14418 }];
      vitest.spyOn(appuserService, 'query').mockReturnValue(of(new HttpResponse({ body: appuserCollection })));
      const additionalAppusers = [followed, following];
      const expectedCollection: IAppuser[] = [...additionalAppusers, ...appuserCollection];
      vitest.spyOn(appuserService, 'addAppuserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ follow });
      comp.ngOnInit();

      expect(appuserService.query).toHaveBeenCalled();
      expect(appuserService.addAppuserToCollectionIfMissing).toHaveBeenCalledWith(
        appuserCollection,
        ...additionalAppusers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.appusersSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Community query and add missing value', () => {
      const follow: IFollow = { id: 10700 };
      const cfollowed: ICommunity = { id: 32684 };
      follow.cfollowed = cfollowed;
      const cfollowing: ICommunity = { id: 32684 };
      follow.cfollowing = cfollowing;

      const communityCollection: ICommunity[] = [{ id: 32684 }];
      vitest.spyOn(communityService, 'query').mockReturnValue(of(new HttpResponse({ body: communityCollection })));
      const additionalCommunities = [cfollowed, cfollowing];
      const expectedCollection: ICommunity[] = [...additionalCommunities, ...communityCollection];
      vitest.spyOn(communityService, 'addCommunityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ follow });
      comp.ngOnInit();

      expect(communityService.query).toHaveBeenCalled();
      expect(communityService.addCommunityToCollectionIfMissing).toHaveBeenCalledWith(
        communityCollection,
        ...additionalCommunities.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.communitiesSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const follow: IFollow = { id: 10700 };
      const followed: IAppuser = { id: 14418 };
      follow.followed = followed;
      const following: IAppuser = { id: 14418 };
      follow.following = following;
      const cfollowed: ICommunity = { id: 32684 };
      follow.cfollowed = cfollowed;
      const cfollowing: ICommunity = { id: 32684 };
      follow.cfollowing = cfollowing;

      activatedRoute.data = of({ follow });
      comp.ngOnInit();

      expect(comp.appusersSharedCollection()).toContainEqual(followed);
      expect(comp.appusersSharedCollection()).toContainEqual(following);
      expect(comp.communitiesSharedCollection()).toContainEqual(cfollowed);
      expect(comp.communitiesSharedCollection()).toContainEqual(cfollowing);
      expect(comp.follow).toEqual(follow);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IFollow>();
      const follow = { id: 30258 };
      vitest.spyOn(followFormService, 'getFollow').mockReturnValue(follow);
      vitest.spyOn(followService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ follow });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(follow);
      saveSubject.complete();

      // THEN
      expect(followFormService.getFollow).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(followService.update).toHaveBeenCalledWith(expect.objectContaining(follow));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IFollow>();
      const follow = { id: 30258 };
      vitest.spyOn(followFormService, 'getFollow').mockReturnValue({ id: null });
      vitest.spyOn(followService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ follow: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(follow);
      saveSubject.complete();

      // THEN
      expect(followFormService.getFollow).toHaveBeenCalled();
      expect(followService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IFollow>();
      const follow = { id: 30258 };
      vitest.spyOn(followService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ follow });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(followService.update).toHaveBeenCalled();
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

    describe('compareCommunity', () => {
      it('should forward to communityService', () => {
        const entity = { id: 32684 };
        const entity2 = { id: 25732 };
        vitest.spyOn(communityService, 'compareCommunity');
        comp.compareCommunity(entity, entity2);
        expect(communityService.compareCommunity).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
