import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../follow.test-samples';

import { FollowFormService } from './follow-form.service';

describe('Follow Form Service', () => {
  let service: FollowFormService;

  beforeEach(() => {
    service = TestBed.inject(FollowFormService);
  });

  describe('Service methods', () => {
    describe('createFollowFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFollowFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            followed: expect.any(Object),
            following: expect.any(Object),
            cfollowed: expect.any(Object),
            cfollowing: expect.any(Object),
          }),
        );
      });

      it('passing IFollow should create a new form with FormGroup', () => {
        const formGroup = service.createFollowFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            followed: expect.any(Object),
            following: expect.any(Object),
            cfollowed: expect.any(Object),
            cfollowing: expect.any(Object),
          }),
        );
      });
    });

    describe('getFollow', () => {
      it('should return NewFollow for default Follow initial value', () => {
        const formGroup = service.createFollowFormGroup(sampleWithNewData);

        const follow = service.getFollow(formGroup);

        expect(follow).toMatchObject(sampleWithNewData);
      });

      it('should return NewFollow for empty Follow initial value', () => {
        const formGroup = service.createFollowFormGroup();

        const follow = service.getFollow(formGroup);

        expect(follow).toMatchObject({});
      });

      it('should return IFollow', () => {
        const formGroup = service.createFollowFormGroup(sampleWithRequiredData);

        const follow = service.getFollow(formGroup);

        expect(follow).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFollow should not enable id FormControl', () => {
        const formGroup = service.createFollowFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFollow should disable id FormControl', () => {
        const formGroup = service.createFollowFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
