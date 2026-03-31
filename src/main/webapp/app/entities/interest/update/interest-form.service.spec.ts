import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../interest.test-samples';

import { InterestFormService } from './interest-form.service';

describe('Interest Form Service', () => {
  let service: InterestFormService;

  beforeEach(() => {
    service = TestBed.inject(InterestFormService);
  });

  describe('Service methods', () => {
    describe('createInterestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInterestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            interestName: expect.any(Object),
            appusers: expect.any(Object),
          }),
        );
      });

      it('passing IInterest should create a new form with FormGroup', () => {
        const formGroup = service.createInterestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            interestName: expect.any(Object),
            appusers: expect.any(Object),
          }),
        );
      });
    });

    describe('getInterest', () => {
      it('should return NewInterest for default Interest initial value', () => {
        const formGroup = service.createInterestFormGroup(sampleWithNewData);

        const interest = service.getInterest(formGroup);

        expect(interest).toMatchObject(sampleWithNewData);
      });

      it('should return NewInterest for empty Interest initial value', () => {
        const formGroup = service.createInterestFormGroup();

        const interest = service.getInterest(formGroup);

        expect(interest).toMatchObject({});
      });

      it('should return IInterest', () => {
        const formGroup = service.createInterestFormGroup(sampleWithRequiredData);

        const interest = service.getInterest(formGroup);

        expect(interest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInterest should not enable id FormControl', () => {
        const formGroup = service.createInterestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInterest should disable id FormControl', () => {
        const formGroup = service.createInterestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
