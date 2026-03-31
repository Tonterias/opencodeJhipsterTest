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
import { IBlockuser } from '../blockuser.model';
import { BlockuserService } from '../service/blockuser.service';

import { BlockuserFormService } from './blockuser-form.service';
import { BlockuserUpdate } from './blockuser-update';

describe('Blockuser Management Update Component', () => {
  let comp: BlockuserUpdate;
  let fixture: ComponentFixture<BlockuserUpdate>;
  let activatedRoute: ActivatedRoute;
  let blockuserFormService: BlockuserFormService;
  let blockuserService: BlockuserService;
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

    fixture = TestBed.createComponent(BlockuserUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    blockuserFormService = TestBed.inject(BlockuserFormService);
    blockuserService = TestBed.inject(BlockuserService);
    appuserService = TestBed.inject(AppuserService);
    communityService = TestBed.inject(CommunityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Appuser query and add missing value', () => {
      const blockuser: IBlockuser = { id: 25341 };
      const blockeduser: IAppuser = { id: 14418 };
      blockuser.blockeduser = blockeduser;
      const blockinguser: IAppuser = { id: 14418 };
      blockuser.blockinguser = blockinguser;

      const appuserCollection: IAppuser[] = [{ id: 14418 }];
      vitest.spyOn(appuserService, 'query').mockReturnValue(of(new HttpResponse({ body: appuserCollection })));
      const additionalAppusers = [blockeduser, blockinguser];
      const expectedCollection: IAppuser[] = [...additionalAppusers, ...appuserCollection];
      vitest.spyOn(appuserService, 'addAppuserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ blockuser });
      comp.ngOnInit();

      expect(appuserService.query).toHaveBeenCalled();
      expect(appuserService.addAppuserToCollectionIfMissing).toHaveBeenCalledWith(
        appuserCollection,
        ...additionalAppusers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.appusersSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Community query and add missing value', () => {
      const blockuser: IBlockuser = { id: 25341 };
      const cblockeduser: ICommunity = { id: 32684 };
      blockuser.cblockeduser = cblockeduser;
      const cblockinguser: ICommunity = { id: 32684 };
      blockuser.cblockinguser = cblockinguser;

      const communityCollection: ICommunity[] = [{ id: 32684 }];
      vitest.spyOn(communityService, 'query').mockReturnValue(of(new HttpResponse({ body: communityCollection })));
      const additionalCommunities = [cblockeduser, cblockinguser];
      const expectedCollection: ICommunity[] = [...additionalCommunities, ...communityCollection];
      vitest.spyOn(communityService, 'addCommunityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ blockuser });
      comp.ngOnInit();

      expect(communityService.query).toHaveBeenCalled();
      expect(communityService.addCommunityToCollectionIfMissing).toHaveBeenCalledWith(
        communityCollection,
        ...additionalCommunities.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.communitiesSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const blockuser: IBlockuser = { id: 25341 };
      const blockeduser: IAppuser = { id: 14418 };
      blockuser.blockeduser = blockeduser;
      const blockinguser: IAppuser = { id: 14418 };
      blockuser.blockinguser = blockinguser;
      const cblockeduser: ICommunity = { id: 32684 };
      blockuser.cblockeduser = cblockeduser;
      const cblockinguser: ICommunity = { id: 32684 };
      blockuser.cblockinguser = cblockinguser;

      activatedRoute.data = of({ blockuser });
      comp.ngOnInit();

      expect(comp.appusersSharedCollection()).toContainEqual(blockeduser);
      expect(comp.appusersSharedCollection()).toContainEqual(blockinguser);
      expect(comp.communitiesSharedCollection()).toContainEqual(cblockeduser);
      expect(comp.communitiesSharedCollection()).toContainEqual(cblockinguser);
      expect(comp.blockuser).toEqual(blockuser);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IBlockuser>();
      const blockuser = { id: 27219 };
      vitest.spyOn(blockuserFormService, 'getBlockuser').mockReturnValue(blockuser);
      vitest.spyOn(blockuserService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ blockuser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(blockuser);
      saveSubject.complete();

      // THEN
      expect(blockuserFormService.getBlockuser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(blockuserService.update).toHaveBeenCalledWith(expect.objectContaining(blockuser));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IBlockuser>();
      const blockuser = { id: 27219 };
      vitest.spyOn(blockuserFormService, 'getBlockuser').mockReturnValue({ id: null });
      vitest.spyOn(blockuserService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ blockuser: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(blockuser);
      saveSubject.complete();

      // THEN
      expect(blockuserFormService.getBlockuser).toHaveBeenCalled();
      expect(blockuserService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IBlockuser>();
      const blockuser = { id: 27219 };
      vitest.spyOn(blockuserService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ blockuser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(blockuserService.update).toHaveBeenCalled();
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
