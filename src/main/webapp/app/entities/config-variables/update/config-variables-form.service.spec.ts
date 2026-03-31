import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../config-variables.test-samples';

import { ConfigVariablesFormService } from './config-variables-form.service';

describe('ConfigVariables Form Service', () => {
  let service: ConfigVariablesFormService;

  beforeEach(() => {
    service = TestBed.inject(ConfigVariablesFormService);
  });

  describe('Service methods', () => {
    describe('createConfigVariablesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createConfigVariablesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            configVarLong1: expect.any(Object),
            configVarLong2: expect.any(Object),
            configVarLong3: expect.any(Object),
            configVarLong4: expect.any(Object),
            configVarLong5: expect.any(Object),
            configVarLong6: expect.any(Object),
            configVarLong7: expect.any(Object),
            configVarLong8: expect.any(Object),
            configVarLong9: expect.any(Object),
            configVarLong10: expect.any(Object),
            configVarLong11: expect.any(Object),
            configVarLong12: expect.any(Object),
            configVarLong13: expect.any(Object),
            configVarLong14: expect.any(Object),
            configVarLong15: expect.any(Object),
            configVarBoolean16: expect.any(Object),
            configVarBoolean17: expect.any(Object),
            configVarBoolean18: expect.any(Object),
            configVarString19: expect.any(Object),
            configVarString20: expect.any(Object),
          }),
        );
      });

      it('passing IConfigVariables should create a new form with FormGroup', () => {
        const formGroup = service.createConfigVariablesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            configVarLong1: expect.any(Object),
            configVarLong2: expect.any(Object),
            configVarLong3: expect.any(Object),
            configVarLong4: expect.any(Object),
            configVarLong5: expect.any(Object),
            configVarLong6: expect.any(Object),
            configVarLong7: expect.any(Object),
            configVarLong8: expect.any(Object),
            configVarLong9: expect.any(Object),
            configVarLong10: expect.any(Object),
            configVarLong11: expect.any(Object),
            configVarLong12: expect.any(Object),
            configVarLong13: expect.any(Object),
            configVarLong14: expect.any(Object),
            configVarLong15: expect.any(Object),
            configVarBoolean16: expect.any(Object),
            configVarBoolean17: expect.any(Object),
            configVarBoolean18: expect.any(Object),
            configVarString19: expect.any(Object),
            configVarString20: expect.any(Object),
          }),
        );
      });
    });

    describe('getConfigVariables', () => {
      it('should return NewConfigVariables for default ConfigVariables initial value', () => {
        const formGroup = service.createConfigVariablesFormGroup(sampleWithNewData);

        const configVariables = service.getConfigVariables(formGroup);

        expect(configVariables).toMatchObject(sampleWithNewData);
      });

      it('should return NewConfigVariables for empty ConfigVariables initial value', () => {
        const formGroup = service.createConfigVariablesFormGroup();

        const configVariables = service.getConfigVariables(formGroup);

        expect(configVariables).toMatchObject({});
      });

      it('should return IConfigVariables', () => {
        const formGroup = service.createConfigVariablesFormGroup(sampleWithRequiredData);

        const configVariables = service.getConfigVariables(formGroup);

        expect(configVariables).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IConfigVariables should not enable id FormControl', () => {
        const formGroup = service.createConfigVariablesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewConfigVariables should disable id FormControl', () => {
        const formGroup = service.createConfigVariablesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
