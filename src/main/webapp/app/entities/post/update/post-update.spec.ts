import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IAppuser } from 'app/entities/appuser/appuser.model';
import { AppuserService } from 'app/entities/appuser/service/appuser.service';
import { IBlog } from 'app/entities/blog/blog.model';
import { BlogService } from 'app/entities/blog/service/blog.service';
import { TagService } from 'app/entities/tag/service/tag.service';
import { ITag } from 'app/entities/tag/tag.model';
import { TopicService } from 'app/entities/topic/service/topic.service';
import { ITopic } from 'app/entities/topic/topic.model';
import { IPost } from '../post.model';
import { PostService } from '../service/post.service';

import { PostFormService } from './post-form.service';
import { PostUpdate } from './post-update';

describe('Post Management Update Component', () => {
  let comp: PostUpdate;
  let fixture: ComponentFixture<PostUpdate>;
  let activatedRoute: ActivatedRoute;
  let postFormService: PostFormService;
  let postService: PostService;
  let appuserService: AppuserService;
  let blogService: BlogService;
  let tagService: TagService;
  let topicService: TopicService;

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

    fixture = TestBed.createComponent(PostUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    postFormService = TestBed.inject(PostFormService);
    postService = TestBed.inject(PostService);
    appuserService = TestBed.inject(AppuserService);
    blogService = TestBed.inject(BlogService);
    tagService = TestBed.inject(TagService);
    topicService = TestBed.inject(TopicService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Appuser query and add missing value', () => {
      const post: IPost = { id: 2872 };
      const appuser: IAppuser = { id: 14418 };
      post.appuser = appuser;

      const appuserCollection: IAppuser[] = [{ id: 14418 }];
      vitest.spyOn(appuserService, 'query').mockReturnValue(of(new HttpResponse({ body: appuserCollection })));
      const additionalAppusers = [appuser];
      const expectedCollection: IAppuser[] = [...additionalAppusers, ...appuserCollection];
      vitest.spyOn(appuserService, 'addAppuserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ post });
      comp.ngOnInit();

      expect(appuserService.query).toHaveBeenCalled();
      expect(appuserService.addAppuserToCollectionIfMissing).toHaveBeenCalledWith(
        appuserCollection,
        ...additionalAppusers.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.appusersSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Blog query and add missing value', () => {
      const post: IPost = { id: 2872 };
      const blog: IBlog = { id: 26836 };
      post.blog = blog;

      const blogCollection: IBlog[] = [{ id: 26836 }];
      vitest.spyOn(blogService, 'query').mockReturnValue(of(new HttpResponse({ body: blogCollection })));
      const additionalBlogs = [blog];
      const expectedCollection: IBlog[] = [...additionalBlogs, ...blogCollection];
      vitest.spyOn(blogService, 'addBlogToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ post });
      comp.ngOnInit();

      expect(blogService.query).toHaveBeenCalled();
      expect(blogService.addBlogToCollectionIfMissing).toHaveBeenCalledWith(
        blogCollection,
        ...additionalBlogs.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.blogsSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Tag query and add missing value', () => {
      const post: IPost = { id: 2872 };
      const tags: ITag[] = [{ id: 19931 }];
      post.tags = tags;

      const tagCollection: ITag[] = [{ id: 19931 }];
      vitest.spyOn(tagService, 'query').mockReturnValue(of(new HttpResponse({ body: tagCollection })));
      const additionalTags = [...tags];
      const expectedCollection: ITag[] = [...additionalTags, ...tagCollection];
      vitest.spyOn(tagService, 'addTagToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ post });
      comp.ngOnInit();

      expect(tagService.query).toHaveBeenCalled();
      expect(tagService.addTagToCollectionIfMissing).toHaveBeenCalledWith(
        tagCollection,
        ...additionalTags.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.tagsSharedCollection()).toEqual(expectedCollection);
    });

    it('should call Topic query and add missing value', () => {
      const post: IPost = { id: 2872 };
      const topics: ITopic[] = [{ id: 29581 }];
      post.topics = topics;

      const topicCollection: ITopic[] = [{ id: 29581 }];
      vitest.spyOn(topicService, 'query').mockReturnValue(of(new HttpResponse({ body: topicCollection })));
      const additionalTopics = [...topics];
      const expectedCollection: ITopic[] = [...additionalTopics, ...topicCollection];
      vitest.spyOn(topicService, 'addTopicToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ post });
      comp.ngOnInit();

      expect(topicService.query).toHaveBeenCalled();
      expect(topicService.addTopicToCollectionIfMissing).toHaveBeenCalledWith(
        topicCollection,
        ...additionalTopics.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.topicsSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const post: IPost = { id: 2872 };
      const appuser: IAppuser = { id: 14418 };
      post.appuser = appuser;
      const blog: IBlog = { id: 26836 };
      post.blog = blog;
      const tag: ITag = { id: 19931 };
      post.tags = [tag];
      const topic: ITopic = { id: 29581 };
      post.topics = [topic];

      activatedRoute.data = of({ post });
      comp.ngOnInit();

      expect(comp.appusersSharedCollection()).toContainEqual(appuser);
      expect(comp.blogsSharedCollection()).toContainEqual(blog);
      expect(comp.tagsSharedCollection()).toContainEqual(tag);
      expect(comp.topicsSharedCollection()).toContainEqual(topic);
      expect(comp.post).toEqual(post);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPost>();
      const post = { id: 21634 };
      vitest.spyOn(postFormService, 'getPost').mockReturnValue(post);
      vitest.spyOn(postService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ post });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(post);
      saveSubject.complete();

      // THEN
      expect(postFormService.getPost).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(postService.update).toHaveBeenCalledWith(expect.objectContaining(post));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IPost>();
      const post = { id: 21634 };
      vitest.spyOn(postFormService, 'getPost').mockReturnValue({ id: null });
      vitest.spyOn(postService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ post: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(post);
      saveSubject.complete();

      // THEN
      expect(postFormService.getPost).toHaveBeenCalled();
      expect(postService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IPost>();
      const post = { id: 21634 };
      vitest.spyOn(postService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ post });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(postService.update).toHaveBeenCalled();
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

    describe('compareBlog', () => {
      it('should forward to blogService', () => {
        const entity = { id: 26836 };
        const entity2 = { id: 18619 };
        vitest.spyOn(blogService, 'compareBlog');
        comp.compareBlog(entity, entity2);
        expect(blogService.compareBlog).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTag', () => {
      it('should forward to tagService', () => {
        const entity = { id: 19931 };
        const entity2 = { id: 16779 };
        vitest.spyOn(tagService, 'compareTag');
        comp.compareTag(entity, entity2);
        expect(tagService.compareTag).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTopic', () => {
      it('should forward to topicService', () => {
        const entity = { id: 29581 };
        const entity2 = { id: 14122 };
        vitest.spyOn(topicService, 'compareTopic');
        comp.compareTopic(entity, entity2);
        expect(topicService.compareTopic).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
