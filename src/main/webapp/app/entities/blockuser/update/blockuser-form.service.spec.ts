import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../blockuser.test-samples';

import { BlockuserFormService } from './blockuser-form.service';

describe('Blockuser Form Service', () => {
  let service: BlockuserFormService;

  beforeEach(() => {
    service = TestBed.inject(BlockuserFormService);
  });

  describe('Service methods', () => {
    describe('createBlockuserFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBlockuserFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            blockeduser: expect.any(Object),
            blockinguser: expect.any(Object),
            cblockeduser: expect.any(Object),
            cblockinguser: expect.any(Object),
          }),
        );
      });

      it('passing IBlockuser should create a new form with FormGroup', () => {
        const formGroup = service.createBlockuserFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            blockeduser: expect.any(Object),
            blockinguser: expect.any(Object),
            cblockeduser: expect.any(Object),
            cblockinguser: expect.any(Object),
          }),
        );
      });
    });

    describe('getBlockuser', () => {
      it('should return NewBlockuser for default Blockuser initial value', () => {
        const formGroup = service.createBlockuserFormGroup(sampleWithNewData);

        const blockuser = service.getBlockuser(formGroup);

        expect(blockuser).toMatchObject(sampleWithNewData);
      });

      it('should return NewBlockuser for empty Blockuser initial value', () => {
        const formGroup = service.createBlockuserFormGroup();

        const blockuser = service.getBlockuser(formGroup);

        expect(blockuser).toMatchObject({});
      });

      it('should return IBlockuser', () => {
        const formGroup = service.createBlockuserFormGroup(sampleWithRequiredData);

        const blockuser = service.getBlockuser(formGroup);

        expect(blockuser).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBlockuser should not enable id FormControl', () => {
        const formGroup = service.createBlockuserFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBlockuser should disable id FormControl', () => {
        const formGroup = service.createBlockuserFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
