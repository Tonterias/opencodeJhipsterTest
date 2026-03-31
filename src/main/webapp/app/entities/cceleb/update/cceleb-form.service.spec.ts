import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cceleb.test-samples';

import { CcelebFormService } from './cceleb-form.service';

describe('Cceleb Form Service', () => {
  let service: CcelebFormService;

  beforeEach(() => {
    service = TestBed.inject(CcelebFormService);
  });

  describe('Service methods', () => {
    describe('createCcelebFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCcelebFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            celebName: expect.any(Object),
            communities: expect.any(Object),
          }),
        );
      });

      it('passing ICceleb should create a new form with FormGroup', () => {
        const formGroup = service.createCcelebFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            celebName: expect.any(Object),
            communities: expect.any(Object),
          }),
        );
      });
    });

    describe('getCceleb', () => {
      it('should return NewCceleb for default Cceleb initial value', () => {
        const formGroup = service.createCcelebFormGroup(sampleWithNewData);

        const cceleb = service.getCceleb(formGroup);

        expect(cceleb).toMatchObject(sampleWithNewData);
      });

      it('should return NewCceleb for empty Cceleb initial value', () => {
        const formGroup = service.createCcelebFormGroup();

        const cceleb = service.getCceleb(formGroup);

        expect(cceleb).toMatchObject({});
      });

      it('should return ICceleb', () => {
        const formGroup = service.createCcelebFormGroup(sampleWithRequiredData);

        const cceleb = service.getCceleb(formGroup);

        expect(cceleb).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICceleb should not enable id FormControl', () => {
        const formGroup = service.createCcelebFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCceleb should disable id FormControl', () => {
        const formGroup = service.createCcelebFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
