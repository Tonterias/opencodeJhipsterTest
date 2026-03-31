import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';
import { ICactivity } from '../cactivity.model';
import { CactivityService } from '../service/cactivity.service';

import { CactivityFormService } from './cactivity-form.service';
import { CactivityUpdate } from './cactivity-update';

describe('Cactivity Management Update Component', () => {
  let comp: CactivityUpdate;
  let fixture: ComponentFixture<CactivityUpdate>;
  let activatedRoute: ActivatedRoute;
  let cactivityFormService: CactivityFormService;
  let cactivityService: CactivityService;
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

    fixture = TestBed.createComponent(CactivityUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cactivityFormService = TestBed.inject(CactivityFormService);
    cactivityService = TestBed.inject(CactivityService);
    communityService = TestBed.inject(CommunityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Community query and add missing value', () => {
      const cactivity: ICactivity = { id: 15121 };
      const communities: ICommunity[] = [{ id: 32684 }];
      cactivity.communities = communities;

      const communityCollection: ICommunity[] = [{ id: 32684 }];
      vitest.spyOn(communityService, 'query').mockReturnValue(of(new HttpResponse({ body: communityCollection })));
      const additionalCommunities = [...communities];
      const expectedCollection: ICommunity[] = [...additionalCommunities, ...communityCollection];
      vitest.spyOn(communityService, 'addCommunityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cactivity });
      comp.ngOnInit();

      expect(communityService.query).toHaveBeenCalled();
      expect(communityService.addCommunityToCollectionIfMissing).toHaveBeenCalledWith(
        communityCollection,
        ...additionalCommunities.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.communitiesSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const cactivity: ICactivity = { id: 15121 };
      const community: ICommunity = { id: 32684 };
      cactivity.communities = [community];

      activatedRoute.data = of({ cactivity });
      comp.ngOnInit();

      expect(comp.communitiesSharedCollection()).toContainEqual(community);
      expect(comp.cactivity).toEqual(cactivity);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<ICactivity>();
      const cactivity = { id: 25738 };
      vitest.spyOn(cactivityFormService, 'getCactivity').mockReturnValue(cactivity);
      vitest.spyOn(cactivityService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cactivity });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(cactivity);
      saveSubject.complete();

      // THEN
      expect(cactivityFormService.getCactivity).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cactivityService.update).toHaveBeenCalledWith(expect.objectContaining(cactivity));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<ICactivity>();
      const cactivity = { id: 25738 };
      vitest.spyOn(cactivityFormService, 'getCactivity').mockReturnValue({ id: null });
      vitest.spyOn(cactivityService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cactivity: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(cactivity);
      saveSubject.complete();

      // THEN
      expect(cactivityFormService.getCactivity).toHaveBeenCalled();
      expect(cactivityService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<ICactivity>();
      const cactivity = { id: 25738 };
      vitest.spyOn(cactivityService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cactivity });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cactivityService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
