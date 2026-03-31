import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../celeb.test-samples';

import { CelebFormService } from './celeb-form.service';

describe('Celeb Form Service', () => {
  let service: CelebFormService;

  beforeEach(() => {
    service = TestBed.inject(CelebFormService);
  });

  describe('Service methods', () => {
    describe('createCelebFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCelebFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            celebName: expect.any(Object),
            appusers: expect.any(Object),
          }),
        );
      });

      it('passing ICeleb should create a new form with FormGroup', () => {
        const formGroup = service.createCelebFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            celebName: expect.any(Object),
            appusers: expect.any(Object),
          }),
        );
      });
    });

    describe('getCeleb', () => {
      it('should return NewCeleb for default Celeb initial value', () => {
        const formGroup = service.createCelebFormGroup(sampleWithNewData);

        const celeb = service.getCeleb(formGroup);

        expect(celeb).toMatchObject(sampleWithNewData);
      });

      it('should return NewCeleb for empty Celeb initial value', () => {
        const formGroup = service.createCelebFormGroup();

        const celeb = service.getCeleb(formGroup);

        expect(celeb).toMatchObject({});
      });

      it('should return ICeleb', () => {
        const formGroup = service.createCelebFormGroup(sampleWithRequiredData);

        const celeb = service.getCeleb(formGroup);

        expect(celeb).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICeleb should not enable id FormControl', () => {
        const formGroup = service.createCelebFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCeleb should disable id FormControl', () => {
        const formGroup = service.createCelebFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
