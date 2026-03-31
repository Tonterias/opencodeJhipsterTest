import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IPost } from 'app/entities/post/post.model';
import { PostService } from 'app/entities/post/service/post.service';
import { TopicService } from '../service/topic.service';
import { ITopic } from '../topic.model';

import { TopicFormService } from './topic-form.service';
import { TopicUpdate } from './topic-update';

describe('Topic Management Update Component', () => {
  let comp: TopicUpdate;
  let fixture: ComponentFixture<TopicUpdate>;
  let activatedRoute: ActivatedRoute;
  let topicFormService: TopicFormService;
  let topicService: TopicService;
  let postService: PostService;

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

    fixture = TestBed.createComponent(TopicUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    topicFormService = TestBed.inject(TopicFormService);
    topicService = TestBed.inject(TopicService);
    postService = TestBed.inject(PostService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Post query and add missing value', () => {
      const topic: ITopic = { id: 14122 };
      const posts: IPost[] = [{ id: 21634 }];
      topic.posts = posts;

      const postCollection: IPost[] = [{ id: 21634 }];
      vitest.spyOn(postService, 'query').mockReturnValue(of(new HttpResponse({ body: postCollection })));
      const additionalPosts = [...posts];
      const expectedCollection: IPost[] = [...additionalPosts, ...postCollection];
      vitest.spyOn(postService, 'addPostToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ topic });
      comp.ngOnInit();

      expect(postService.query).toHaveBeenCalled();
      expect(postService.addPostToCollectionIfMissing).toHaveBeenCalledWith(
        postCollection,
        ...additionalPosts.map(i => expect.objectContaining(i) as typeof i),
      );
      expect(comp.postsSharedCollection()).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const topic: ITopic = { id: 14122 };
      const post: IPost = { id: 21634 };
      topic.posts = [post];

      activatedRoute.data = of({ topic });
      comp.ngOnInit();

      expect(comp.postsSharedCollection()).toContainEqual(post);
      expect(comp.topic).toEqual(topic);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<ITopic>();
      const topic = { id: 29581 };
      vitest.spyOn(topicFormService, 'getTopic').mockReturnValue(topic);
      vitest.spyOn(topicService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ topic });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(topic);
      saveSubject.complete();

      // THEN
      expect(topicFormService.getTopic).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(topicService.update).toHaveBeenCalledWith(expect.objectContaining(topic));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<ITopic>();
      const topic = { id: 29581 };
      vitest.spyOn(topicFormService, 'getTopic').mockReturnValue({ id: null });
      vitest.spyOn(topicService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ topic: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(topic);
      saveSubject.complete();

      // THEN
      expect(topicFormService.getTopic).toHaveBeenCalled();
      expect(topicService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<ITopic>();
      const topic = { id: 29581 };
      vitest.spyOn(topicService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ topic });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(topicService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePost', () => {
      it('should forward to postService', () => {
        const entity = { id: 21634 };
        const entity2 = { id: 2872 };
        vitest.spyOn(postService, 'comparePost');
        comp.comparePost(entity, entity2);
        expect(postService.comparePost).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
