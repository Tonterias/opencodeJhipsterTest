import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cinterest.test-samples';

import { CinterestFormService } from './cinterest-form.service';

describe('Cinterest Form Service', () => {
  let service: CinterestFormService;

  beforeEach(() => {
    service = TestBed.inject(CinterestFormService);
  });

  describe('Service methods', () => {
    describe('createCinterestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCinterestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            interestName: expect.any(Object),
            communities: expect.any(Object),
          }),
        );
      });

      it('passing ICinterest should create a new form with FormGroup', () => {
        const formGroup = service.createCinterestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            interestName: expect.any(Object),
            communities: expect.any(Object),
          }),
        );
      });
    });

    describe('getCinterest', () => {
      it('should return NewCinterest for default Cinterest initial value', () => {
        const formGroup = service.createCinterestFormGroup(sampleWithNewData);

        const cinterest = service.getCinterest(formGroup);

        expect(cinterest).toMatchObject(sampleWithNewData);
      });

      it('should return NewCinterest for empty Cinterest initial value', () => {
        const formGroup = service.createCinterestFormGroup();

        const cinterest = service.getCinterest(formGroup);

        expect(cinterest).toMatchObject({});
      });

      it('should return ICinterest', () => {
        const formGroup = service.createCinterestFormGroup(sampleWithRequiredData);

        const cinterest = service.getCinterest(formGroup);

        expect(cinterest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICinterest should not enable id FormControl', () => {
        const formGroup = service.createCinterestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCinterest should disable id FormControl', () => {
        const formGroup = service.createCinterestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
