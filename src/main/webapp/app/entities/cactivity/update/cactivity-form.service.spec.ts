import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cactivity.test-samples';

import { CactivityFormService } from './cactivity-form.service';

describe('Cactivity Form Service', () => {
  let service: CactivityFormService;

  beforeEach(() => {
    service = TestBed.inject(CactivityFormService);
  });

  describe('Service methods', () => {
    describe('createCactivityFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCactivityFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            activityName: expect.any(Object),
            communities: expect.any(Object),
          }),
        );
      });

      it('passing ICactivity should create a new form with FormGroup', () => {
        const formGroup = service.createCactivityFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            activityName: expect.any(Object),
            communities: expect.any(Object),
          }),
        );
      });
    });

    describe('getCactivity', () => {
      it('should return NewCactivity for default Cactivity initial value', () => {
        const formGroup = service.createCactivityFormGroup(sampleWithNewData);

        const cactivity = service.getCactivity(formGroup);

        expect(cactivity).toMatchObject(sampleWithNewData);
      });

      it('should return NewCactivity for empty Cactivity initial value', () => {
        const formGroup = service.createCactivityFormGroup();

        const cactivity = service.getCactivity(formGroup);

        expect(cactivity).toMatchObject({});
      });

      it('should return ICactivity', () => {
        const formGroup = service.createCactivityFormGroup(sampleWithRequiredData);

        const cactivity = service.getCactivity(formGroup);

        expect(cactivity).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICactivity should not enable id FormControl', () => {
        const formGroup = service.createCactivityFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCactivity should disable id FormControl', () => {
        const formGroup = service.createCactivityFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
