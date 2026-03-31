import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../frontpageconfig.test-samples';

import { FrontpageconfigFormService } from './frontpageconfig-form.service';

describe('Frontpageconfig Form Service', () => {
  let service: FrontpageconfigFormService;

  beforeEach(() => {
    service = TestBed.inject(FrontpageconfigFormService);
  });

  describe('Service methods', () => {
    describe('createFrontpageconfigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFrontpageconfigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            topNews1: expect.any(Object),
            topNews2: expect.any(Object),
            topNews3: expect.any(Object),
            topNews4: expect.any(Object),
            topNews5: expect.any(Object),
            latestNews1: expect.any(Object),
            latestNews2: expect.any(Object),
            latestNews3: expect.any(Object),
            latestNews4: expect.any(Object),
            latestNews5: expect.any(Object),
            breakingNews1: expect.any(Object),
            recentPosts1: expect.any(Object),
            recentPosts2: expect.any(Object),
            recentPosts3: expect.any(Object),
            recentPosts4: expect.any(Object),
            featuredArticles1: expect.any(Object),
            featuredArticles2: expect.any(Object),
            featuredArticles3: expect.any(Object),
            featuredArticles4: expect.any(Object),
            featuredArticles5: expect.any(Object),
            featuredArticles6: expect.any(Object),
            featuredArticles7: expect.any(Object),
            featuredArticles8: expect.any(Object),
            featuredArticles9: expect.any(Object),
            featuredArticles10: expect.any(Object),
            popularNews1: expect.any(Object),
            popularNews2: expect.any(Object),
            popularNews3: expect.any(Object),
            popularNews4: expect.any(Object),
            popularNews5: expect.any(Object),
            popularNews6: expect.any(Object),
            popularNews7: expect.any(Object),
            popularNews8: expect.any(Object),
            weeklyNews1: expect.any(Object),
            weeklyNews2: expect.any(Object),
            weeklyNews3: expect.any(Object),
            weeklyNews4: expect.any(Object),
            newsFeeds1: expect.any(Object),
            newsFeeds2: expect.any(Object),
            newsFeeds3: expect.any(Object),
            newsFeeds4: expect.any(Object),
            newsFeeds5: expect.any(Object),
            newsFeeds6: expect.any(Object),
            usefulLinks1: expect.any(Object),
            usefulLinks2: expect.any(Object),
            usefulLinks3: expect.any(Object),
            usefulLinks4: expect.any(Object),
            usefulLinks5: expect.any(Object),
            usefulLinks6: expect.any(Object),
            recentVideos1: expect.any(Object),
            recentVideos2: expect.any(Object),
            recentVideos3: expect.any(Object),
            recentVideos4: expect.any(Object),
            recentVideos5: expect.any(Object),
            recentVideos6: expect.any(Object),
          }),
        );
      });

      it('passing IFrontpageconfig should create a new form with FormGroup', () => {
        const formGroup = service.createFrontpageconfigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            topNews1: expect.any(Object),
            topNews2: expect.any(Object),
            topNews3: expect.any(Object),
            topNews4: expect.any(Object),
            topNews5: expect.any(Object),
            latestNews1: expect.any(Object),
            latestNews2: expect.any(Object),
            latestNews3: expect.any(Object),
            latestNews4: expect.any(Object),
            latestNews5: expect.any(Object),
            breakingNews1: expect.any(Object),
            recentPosts1: expect.any(Object),
            recentPosts2: expect.any(Object),
            recentPosts3: expect.any(Object),
            recentPosts4: expect.any(Object),
            featuredArticles1: expect.any(Object),
            featuredArticles2: expect.any(Object),
            featuredArticles3: expect.any(Object),
            featuredArticles4: expect.any(Object),
            featuredArticles5: expect.any(Object),
            featuredArticles6: expect.any(Object),
            featuredArticles7: expect.any(Object),
            featuredArticles8: expect.any(Object),
            featuredArticles9: expect.any(Object),
            featuredArticles10: expect.any(Object),
            popularNews1: expect.any(Object),
            popularNews2: expect.any(Object),
            popularNews3: expect.any(Object),
            popularNews4: expect.any(Object),
            popularNews5: expect.any(Object),
            popularNews6: expect.any(Object),
            popularNews7: expect.any(Object),
            popularNews8: expect.any(Object),
            weeklyNews1: expect.any(Object),
            weeklyNews2: expect.any(Object),
            weeklyNews3: expect.any(Object),
            weeklyNews4: expect.any(Object),
            newsFeeds1: expect.any(Object),
            newsFeeds2: expect.any(Object),
            newsFeeds3: expect.any(Object),
            newsFeeds4: expect.any(Object),
            newsFeeds5: expect.any(Object),
            newsFeeds6: expect.any(Object),
            usefulLinks1: expect.any(Object),
            usefulLinks2: expect.any(Object),
            usefulLinks3: expect.any(Object),
            usefulLinks4: expect.any(Object),
            usefulLinks5: expect.any(Object),
            usefulLinks6: expect.any(Object),
            recentVideos1: expect.any(Object),
            recentVideos2: expect.any(Object),
            recentVideos3: expect.any(Object),
            recentVideos4: expect.any(Object),
            recentVideos5: expect.any(Object),
            recentVideos6: expect.any(Object),
          }),
        );
      });
    });

    describe('getFrontpageconfig', () => {
      it('should return NewFrontpageconfig for default Frontpageconfig initial value', () => {
        const formGroup = service.createFrontpageconfigFormGroup(sampleWithNewData);

        const frontpageconfig = service.getFrontpageconfig(formGroup);

        expect(frontpageconfig).toMatchObject(sampleWithNewData);
      });

      it('should return NewFrontpageconfig for empty Frontpageconfig initial value', () => {
        const formGroup = service.createFrontpageconfigFormGroup();

        const frontpageconfig = service.getFrontpageconfig(formGroup);

        expect(frontpageconfig).toMatchObject({});
      });

      it('should return IFrontpageconfig', () => {
        const formGroup = service.createFrontpageconfigFormGroup(sampleWithRequiredData);

        const frontpageconfig = service.getFrontpageconfig(formGroup);

        expect(frontpageconfig).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFrontpageconfig should not enable id FormControl', () => {
        const formGroup = service.createFrontpageconfigFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFrontpageconfig should disable id FormControl', () => {
        const formGroup = service.createFrontpageconfigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
