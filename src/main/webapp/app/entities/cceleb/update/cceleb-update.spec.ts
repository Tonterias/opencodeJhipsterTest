import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';
import { ICceleb } from '../cceleb.model';
import { CcelebService } from '../service/cceleb.service';

import { CcelebFormService } from './cceleb-form.service';
import { CcelebUpdate } from './cceleb-update';

describe('Cceleb Management Update Component', () => {
  let comp: CcelebUpdate;
  let fixture: ComponentFixture<CcelebUpdate>;
  let activatedRoute: ActivatedRoute;
  let ccelebFormService: CcelebFormService;
  let ccelebService: CcelebService;
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

    fixture = TestBed.createComponent(CcelebUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ccelebFormService = TestBed.inject(CcelebFormService);
    ccelebService = TestBed.inject(CcelebService);
    communityService = TestBed.inject(CommunityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Community query and add missing value', () => {
      const cceleb: ICceleb = { id: 19336 };
      const communities: ICommunity[] = [{ id: 32684 }];
      cceleb.communities = communities;

      const communityCollection: ICommunity[] = [{ id: 32684 }];
      vitest.spyOn(communityService, 'query').mockReturnValue(of(new HttpResponse({ body: communityCollection })));
      const additionalCommunities = [...communities];
      const expectedCollection: ICommunity[] = [...additionalCommunities, ...communityCollection];
      vitest.spyOn(communityService, 'addCommunityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cceleb });
      comp.ngOnInit();

      expect(communityService.query).toHaveBeenCalled();
      expect(communityService.addCommunityToCollectionIfMissing).toHaveBeenCalledWith(
        communityCollection,
        ...additionalCommunities.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.communitiesSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const cceleb: ICceleb = { id: 19336 };
      const community: ICommunity = { id: 32684 };
      cceleb.communities = [community];

      activatedRoute.data = of({ cceleb });
      comp.ngOnInit();

      expect(comp.communitiesSharedCollection()).toContainEqual(community);
      expect(comp.cceleb).toEqual(cceleb);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<ICceleb>();
      const cceleb = { id: 27907 };
      vitest.spyOn(ccelebFormService, 'getCceleb').mockReturnValue(cceleb);
      vitest.spyOn(ccelebService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cceleb });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(cceleb);
      saveSubject.complete();

      // THEN
      expect(ccelebFormService.getCceleb).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ccelebService.update).toHaveBeenCalledWith(expect.objectContaining(cceleb));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<ICceleb>();
      const cceleb = { id: 27907 };
      vitest.spyOn(ccelebFormService, 'getCceleb').mockReturnValue({ id: null });
      vitest.spyOn(ccelebService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cceleb: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(cceleb);
      saveSubject.complete();

      // THEN
      expect(ccelebFormService.getCceleb).toHaveBeenCalled();
      expect(ccelebService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<ICceleb>();
      const cceleb = { id: 27907 };
      vitest.spyOn(ccelebService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cceleb });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ccelebService.update).toHaveBeenCalled();
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
