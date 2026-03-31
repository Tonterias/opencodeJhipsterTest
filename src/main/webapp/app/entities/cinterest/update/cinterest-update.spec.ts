import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';
import { ICinterest } from '../cinterest.model';
import { CinterestService } from '../service/cinterest.service';

import { CinterestFormService } from './cinterest-form.service';
import { CinterestUpdate } from './cinterest-update';

describe('Cinterest Management Update Component', () => {
  let comp: CinterestUpdate;
  let fixture: ComponentFixture<CinterestUpdate>;
  let activatedRoute: ActivatedRoute;
  let cinterestFormService: CinterestFormService;
  let cinterestService: CinterestService;
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

    fixture = TestBed.createComponent(CinterestUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cinterestFormService = TestBed.inject(CinterestFormService);
    cinterestService = TestBed.inject(CinterestService);
    communityService = TestBed.inject(CommunityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Community query and add missing value', () => {
      const cinterest: ICinterest = { id: 31193 };
      const communities: ICommunity[] = [{ id: 32684 }];
      cinterest.communities = communities;

      const communityCollection: ICommunity[] = [{ id: 32684 }];
      vitest.spyOn(communityService, 'query').mockReturnValue(of(new HttpResponse({ body: communityCollection })));
      const additionalCommunities = [...communities];
      const expectedCollection: ICommunity[] = [...additionalCommunities, ...communityCollection];
      vitest.spyOn(communityService, 'addCommunityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cinterest });
      comp.ngOnInit();

      expect(communityService.query).toHaveBeenCalled();
      expect(communityService.addCommunityToCollectionIfMissing).toHaveBeenCalledWith(
        communityCollection,
        ...additionalCommunities.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.communitiesSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const cinterest: ICinterest = { id: 31193 };
      const community: ICommunity = { id: 32684 };
      cinterest.communities = [community];

      activatedRoute.data = of({ cinterest });
      comp.ngOnInit();

      expect(comp.communitiesSharedCollection()).toContainEqual(community);
      expect(comp.cinterest).toEqual(cinterest);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<ICinterest>();
      const cinterest = { id: 11835 };
      vitest.spyOn(cinterestFormService, 'getCinterest').mockReturnValue(cinterest);
      vitest.spyOn(cinterestService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cinterest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(cinterest);
      saveSubject.complete();

      // THEN
      expect(cinterestFormService.getCinterest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cinterestService.update).toHaveBeenCalledWith(expect.objectContaining(cinterest));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<ICinterest>();
      const cinterest = { id: 11835 };
      vitest.spyOn(cinterestFormService, 'getCinterest').mockReturnValue({ id: null });
      vitest.spyOn(cinterestService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cinterest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(cinterest);
      saveSubject.complete();

      // THEN
      expect(cinterestFormService.getCinterest).toHaveBeenCalled();
      expect(cinterestService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<ICinterest>();
      const cinterest = { id: 11835 };
      vitest.spyOn(cinterestService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cinterest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cinterestService.update).toHaveBeenCalled();
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
