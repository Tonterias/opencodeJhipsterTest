import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../community.test-samples';

import { CommunityFormService } from './community-form.service';

describe('Community Form Service', () => {
  let service: CommunityFormService;

  beforeEach(() => {
    service = TestBed.inject(CommunityFormService);
  });

  describe('Service methods', () => {
    describe('createCommunityFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCommunityFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            communityName: expect.any(Object),
            communityDescription: expect.any(Object),
            image: expect.any(Object),
            isActive: expect.any(Object),
            appuser: expect.any(Object),
            cinterests: expect.any(Object),
            cactivities: expect.any(Object),
            ccelebs: expect.any(Object),
          }),
        );
      });

      it('passing ICommunity should create a new form with FormGroup', () => {
        const formGroup = service.createCommunityFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            communityName: expect.any(Object),
            communityDescription: expect.any(Object),
            image: expect.any(Object),
            isActive: expect.any(Object),
            appuser: expect.any(Object),
            cinterests: expect.any(Object),
            cactivities: expect.any(Object),
            ccelebs: expect.any(Object),
          }),
        );
      });
    });

    describe('getCommunity', () => {
      it('should return NewCommunity for default Community initial value', () => {
        const formGroup = service.createCommunityFormGroup(sampleWithNewData);

        const community = service.getCommunity(formGroup);

        expect(community).toMatchObject(sampleWithNewData);
      });

      it('should return NewCommunity for empty Community initial value', () => {
        const formGroup = service.createCommunityFormGroup();

        const community = service.getCommunity(formGroup);

        expect(community).toMatchObject({});
      });

      it('should return ICommunity', () => {
        const formGroup = service.createCommunityFormGroup(sampleWithRequiredData);

        const community = service.getCommunity(formGroup);

        expect(community).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICommunity should not enable id FormControl', () => {
        const formGroup = service.createCommunityFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCommunity should disable id FormControl', () => {
        const formGroup = service.createCommunityFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
