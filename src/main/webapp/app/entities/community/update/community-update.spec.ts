import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { AppuserService } from 'app/entities/appuser/service/appuser.service';
import { ICactivity } from 'app/entities/cactivity/cactivity.model';
import { CactivityService } from 'app/entities/cactivity/service/cactivity.service';
import { ICceleb } from 'app/entities/cceleb/cceleb.model';
import { CcelebService } from 'app/entities/cceleb/service/cceleb.service';
import { ICinterest } from 'app/entities/cinterest/cinterest.model';
import { CinterestService } from 'app/entities/cinterest/service/cinterest.service';
import { ICommunity } from '../community.model';
import { CommunityService } from '../service/community.service';

import { CommunityFormService } from './community-form.service';
import { CommunityUpdate } from './community-update';

describe('Community Management Update Component', () => {
  let comp: CommunityUpdate;
  let fixture: ComponentFixture<CommunityUpdate>;
  let activatedRoute: ActivatedRoute;
  let communityFormService: CommunityFormService;
  let communityService: CommunityService;
  let appuserService: AppuserService;
  let cinterestService: CinterestService;
  let cactivityService: CactivityService;
  let ccelebService: CcelebService;

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

    fixture = TestBed.createComponent(CommunityUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    communityFormService = TestBed.inject(CommunityFormService);
    communityService = TestBed.inject(CommunityService);
    appuserService = TestBed.inject(AppuserService);
    cinterestService = TestBed.inject(CinterestService);
    cactivityService = TestBed.inject(CactivityService);
    ccelebService = TestBed.inject(CcelebService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Appuser query and add missing value', () => {
      const community: ICommunity = { id: 25732 };
      const appuser: IAppuser = { id: 14418 };
      community.appuser = appuser;

      const appuserCollection: IAppuser[] = [{ id: 14418 }];
      vitest.spyOn(appuserService, 'query').mockReturnValue(of(new HttpResponse({ body: appuserCollection })));
      const additionalAppusers = [appuser];
      const expectedCollection: IAppuser[] = [...additionalAppusers, ...appuserCollection];
      vitest.spyOn(appuserService, 'addAppuserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ community });
      comp.ngOnInit();

      expect(appuserService.query).toHaveBeenCalled();
      expect(appuserService.addAppuserToCollectionIfMissing).toHaveBeenCalledWith(
        appuserCollection,
        ...additionalAppusers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.appusersSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Cinterest query and add missing value', () => {
      const community: ICommunity = { id: 25732 };
      const cinterests: ICinterest[] = [{ id: 11835 }];
      community.cinterests = cinterests;

      const cinterestCollection: ICinterest[] = [{ id: 11835 }];
      vitest.spyOn(cinterestService, 'query').mockReturnValue(of(new HttpResponse({ body: cinterestCollection })));
      const additionalCinterests = [...cinterests];
      const expectedCollection: ICinterest[] = [...additionalCinterests, ...cinterestCollection];
      vitest.spyOn(cinterestService, 'addCinterestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ community });
      comp.ngOnInit();

      expect(cinterestService.query).toHaveBeenCalled();
      expect(cinterestService.addCinterestToCollectionIfMissing).toHaveBeenCalledWith(
        cinterestCollection,
        ...additionalCinterests.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.cinterestsSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Cactivity query and add missing value', () => {
      const community: ICommunity = { id: 25732 };
      const cactivities: ICactivity[] = [{ id: 25738 }];
      community.cactivities = cactivities;

      const cactivityCollection: ICactivity[] = [{ id: 25738 }];
      vitest.spyOn(cactivityService, 'query').mockReturnValue(of(new HttpResponse({ body: cactivityCollection })));
      const additionalCactivities = [...cactivities];
      const expectedCollection: ICactivity[] = [...additionalCactivities, ...cactivityCollection];
      vitest.spyOn(cactivityService, 'addCactivityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ community });
      comp.ngOnInit();

      expect(cactivityService.query).toHaveBeenCalled();
      expect(cactivityService.addCactivityToCollectionIfMissing).toHaveBeenCalledWith(
        cactivityCollection,
        ...additionalCactivities.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.cactivitiesSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Cceleb query and add missing value', () => {
      const community: ICommunity = { id: 25732 };
      const ccelebs: ICceleb[] = [{ id: 27907 }];
      community.ccelebs = ccelebs;

      const ccelebCollection: ICceleb[] = [{ id: 27907 }];
      vitest.spyOn(ccelebService, 'query').mockReturnValue(of(new HttpResponse({ body: ccelebCollection })));
      const additionalCcelebs = [...ccelebs];
      const expectedCollection: ICceleb[] = [...additionalCcelebs, ...ccelebCollection];
      vitest.spyOn(ccelebService, 'addCcelebToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ community });
      comp.ngOnInit();

      expect(ccelebService.query).toHaveBeenCalled();
      expect(ccelebService.addCcelebToCollectionIfMissing).toHaveBeenCalledWith(
        ccelebCollection,
        ...additionalCcelebs.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.ccelebsSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const community: ICommunity = { id: 25732 };
      const appuser: IAppuser = { id: 14418 };
      community.appuser = appuser;
      const cinterest: ICinterest = { id: 11835 };
      community.cinterests = [cinterest];
      const cactivity: ICactivity = { id: 25738 };
      community.cactivities = [cactivity];
      const cceleb: ICceleb = { id: 27907 };
      community.ccelebs = [cceleb];

      activatedRoute.data = of({ community });
      comp.ngOnInit();

      expect(comp.appusersSharedCollection()).toContainEqual(appuser);
      expect(comp.cinterestsSharedCollection()).toContainEqual(cinterest);
      expect(comp.cactivitiesSharedCollection()).toContainEqual(cactivity);
      expect(comp.ccelebsSharedCollection()).toContainEqual(cceleb);
      expect(comp.community).toEqual(community);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<ICommunity>();
      const community = { id: 32684 };
      vitest.spyOn(communityFormService, 'getCommunity').mockReturnValue(community);
      vitest.spyOn(communityService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ community });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(community);
      saveSubject.complete();

      // THEN
      expect(communityFormService.getCommunity).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(communityService.update).toHaveBeenCalledWith(expect.objectContaining(community));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<ICommunity>();
      const community = { id: 32684 };
      vitest.spyOn(communityFormService, 'getCommunity').mockReturnValue({ id: null });
      vitest.spyOn(communityService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ community: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(community);
      saveSubject.complete();

      // THEN
      expect(communityFormService.getCommunity).toHaveBeenCalled();
      expect(communityService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<ICommunity>();
      const community = { id: 32684 };
      vitest.spyOn(communityService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ community });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(communityService.update).toHaveBeenCalled();
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

    describe('compareCinterest', () => {
      it('should forward to cinterestService', () => {
        const entity = { id: 11835 };
        const entity2 = { id: 31193 };
        vitest.spyOn(cinterestService, 'compareCinterest');
        comp.compareCinterest(entity, entity2);
        expect(cinterestService.compareCinterest).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCactivity', () => {
      it('should forward to cactivityService', () => {
        const entity = { id: 25738 };
        const entity2 = { id: 15121 };
        vitest.spyOn(cactivityService, 'compareCactivity');
        comp.compareCactivity(entity, entity2);
        expect(cactivityService.compareCactivity).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCceleb', () => {
      it('should forward to ccelebService', () => {
        const entity = { id: 27907 };
        const entity2 = { id: 19336 };
        vitest.spyOn(ccelebService, 'compareCceleb');
        comp.compareCceleb(entity, entity2);
        expect(ccelebService.compareCceleb).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
