import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../urllink.test-samples';

import { UrllinkFormService } from './urllink-form.service';

describe('Urllink Form Service', () => {
  let service: UrllinkFormService;

  beforeEach(() => {
    service = TestBed.inject(UrllinkFormService);
  });

  describe('Service methods', () => {
    describe('createUrllinkFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUrllinkFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            linkText: expect.any(Object),
            linkURL: expect.any(Object),
          }),
        );
      });

      it('passing IUrllink should create a new form with FormGroup', () => {
        const formGroup = service.createUrllinkFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            linkText: expect.any(Object),
            linkURL: expect.any(Object),
          }),
        );
      });
    });

    describe('getUrllink', () => {
      it('should return NewUrllink for default Urllink initial value', () => {
        const formGroup = service.createUrllinkFormGroup(sampleWithNewData);

        const urllink = service.getUrllink(formGroup);

        expect(urllink).toMatchObject(sampleWithNewData);
      });

      it('should return NewUrllink for empty Urllink initial value', () => {
        const formGroup = service.createUrllinkFormGroup();

        const urllink = service.getUrllink(formGroup);

        expect(urllink).toMatchObject({});
      });

      it('should return IUrllink', () => {
        const formGroup = service.createUrllinkFormGroup(sampleWithRequiredData);

        const urllink = service.getUrllink(formGroup);

        expect(urllink).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUrllink should not enable id FormControl', () => {
        const formGroup = service.createUrllinkFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUrllink should disable id FormControl', () => {
        const formGroup = service.createUrllinkFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
