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
import { IBlog } from '../blog.model';
import { BlogService } from '../service/blog.service';

import { BlogFormService } from './blog-form.service';
import { BlogUpdate } from './blog-update';

describe('Blog Management Update Component', () => {
  let comp: BlogUpdate;
  let fixture: ComponentFixture<BlogUpdate>;
  let activatedRoute: ActivatedRoute;
  let blogFormService: BlogFormService;
  let blogService: BlogService;
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

    fixture = TestBed.createComponent(BlogUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    blogFormService = TestBed.inject(BlogFormService);
    blogService = TestBed.inject(BlogService);
    appuserService = TestBed.inject(AppuserService);
    communityService = TestBed.inject(CommunityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Appuser query and add missing value', () => {
      const blog: IBlog = { id: 18619 };
      const appuser: IAppuser = { id: 14418 };
      blog.appuser = appuser;

      const appuserCollection: IAppuser[] = [{ id: 14418 }];
      vitest.spyOn(appuserService, 'query').mockReturnValue(of(new HttpResponse({ body: appuserCollection })));
      const additionalAppusers = [appuser];
      const expectedCollection: IAppuser[] = [...additionalAppusers, ...appuserCollection];
      vitest.spyOn(appuserService, 'addAppuserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ blog });
      comp.ngOnInit();

      expect(appuserService.query).toHaveBeenCalled();
      expect(appuserService.addAppuserToCollectionIfMissing).toHaveBeenCalledWith(
        appuserCollection,
        ...additionalAppusers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.appusersSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Community query and add missing value', () => {
      const blog: IBlog = { id: 18619 };
      const community: ICommunity = { id: 32684 };
      blog.community = community;

      const communityCollection: ICommunity[] = [{ id: 32684 }];
      vitest.spyOn(communityService, 'query').mockReturnValue(of(new HttpResponse({ body: communityCollection })));
      const additionalCommunities = [community];
      const expectedCollection: ICommunity[] = [...additionalCommunities, ...communityCollection];
      vitest.spyOn(communityService, 'addCommunityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ blog });
      comp.ngOnInit();

      expect(communityService.query).toHaveBeenCalled();
      expect(communityService.addCommunityToCollectionIfMissing).toHaveBeenCalledWith(
        communityCollection,
        ...additionalCommunities.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.communitiesSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const blog: IBlog = { id: 18619 };
      const appuser: IAppuser = { id: 14418 };
      blog.appuser = appuser;
      const community: ICommunity = { id: 32684 };
      blog.community = community;

      activatedRoute.data = of({ blog });
      comp.ngOnInit();

      expect(comp.appusersSharedCollection()).toContainEqual(appuser);
      expect(comp.communitiesSharedCollection()).toContainEqual(community);
      expect(comp.blog).toEqual(blog);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IBlog>();
      const blog = { id: 26836 };
      vitest.spyOn(blogFormService, 'getBlog').mockReturnValue(blog);
      vitest.spyOn(blogService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ blog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(blog);
      saveSubject.complete();

      // THEN
      expect(blogFormService.getBlog).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(blogService.update).toHaveBeenCalledWith(expect.objectContaining(blog));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IBlog>();
      const blog = { id: 26836 };
      vitest.spyOn(blogFormService, 'getBlog').mockReturnValue({ id: null });
      vitest.spyOn(blogService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ blog: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(blog);
      saveSubject.complete();

      // THEN
      expect(blogFormService.getBlog).toHaveBeenCalled();
      expect(blogService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IBlog>();
      const blog = { id: 26836 };
      vitest.spyOn(blogService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ blog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(blogService.update).toHaveBeenCalled();
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
