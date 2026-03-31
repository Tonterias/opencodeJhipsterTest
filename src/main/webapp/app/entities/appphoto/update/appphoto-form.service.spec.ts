import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../appphoto.test-samples';

import { AppphotoFormService } from './appphoto-form.service';

describe('Appphoto Form Service', () => {
  let service: AppphotoFormService;

  beforeEach(() => {
    service = TestBed.inject(AppphotoFormService);
  });

  describe('Service methods', () => {
    describe('createAppphotoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAppphotoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            image: expect.any(Object),
            appuser: expect.any(Object),
          }),
        );
      });

      it('passing IAppphoto should create a new form with FormGroup', () => {
        const formGroup = service.createAppphotoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            image: expect.any(Object),
            appuser: expect.any(Object),
          }),
        );
      });
    });

    describe('getAppphoto', () => {
      it('should return NewAppphoto for default Appphoto initial value', () => {
        const formGroup = service.createAppphotoFormGroup(sampleWithNewData);

        const appphoto = service.getAppphoto(formGroup);

        expect(appphoto).toMatchObject(sampleWithNewData);
      });

      it('should return NewAppphoto for empty Appphoto initial value', () => {
        const formGroup = service.createAppphotoFormGroup();

        const appphoto = service.getAppphoto(formGroup);

        expect(appphoto).toMatchObject({});
      });

      it('should return IAppphoto', () => {
        const formGroup = service.createAppphotoFormGroup(sampleWithRequiredData);

        const appphoto = service.getAppphoto(formGroup);

        expect(appphoto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAppphoto should not enable id FormControl', () => {
        const formGroup = service.createAppphotoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAppphoto should disable id FormControl', () => {
        const formGroup = service.createAppphotoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
