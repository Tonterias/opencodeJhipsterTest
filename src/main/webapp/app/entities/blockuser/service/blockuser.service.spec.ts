import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IBlockuser } from '../blockuser.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../blockuser.test-samples';

import { BlockuserService, RestBlockuser } from './blockuser.service';

const requireRestSample: RestBlockuser = {
  ...sampleWithRequiredData,
  creationDate: sampleWithRequiredData.creationDate?.toJSON(),
};

describe('Blockuser Service', () => {
  let service: BlockuserService;
  let httpMock: HttpTestingController;
  let expectedResult: IBlockuser | IBlockuser[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(BlockuserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Blockuser', () => {
      const blockuser = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(blockuser).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Blockuser', () => {
      const blockuser = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(blockuser).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Blockuser', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Blockuser', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Blockuser', () => {
      service.delete(123).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addBlockuserToCollectionIfMissing', () => {
      it('should add a Blockuser to an empty array', () => {
        const blockuser: IBlockuser = sampleWithRequiredData;
        expectedResult = service.addBlockuserToCollectionIfMissing([], blockuser);
        expect(expectedResult).toEqual([blockuser]);
      });

      it('should not add a Blockuser to an array that contains it', () => {
        const blockuser: IBlockuser = sampleWithRequiredData;
        const blockuserCollection: IBlockuser[] = [
          {
            ...blockuser,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBlockuserToCollectionIfMissing(blockuserCollection, blockuser);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Blockuser to an array that doesn't contain it", () => {
        const blockuser: IBlockuser = sampleWithRequiredData;
        const blockuserCollection: IBlockuser[] = [sampleWithPartialData];
        expectedResult = service.addBlockuserToCollectionIfMissing(blockuserCollection, blockuser);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(blockuser);
      });

      it('should add only unique Blockuser to an array', () => {
        const blockuserArray: IBlockuser[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const blockuserCollection: IBlockuser[] = [sampleWithRequiredData];
        expectedResult = service.addBlockuserToCollectionIfMissing(blockuserCollection, ...blockuserArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const blockuser: IBlockuser = sampleWithRequiredData;
        const blockuser2: IBlockuser = sampleWithPartialData;
        expectedResult = service.addBlockuserToCollectionIfMissing([], blockuser, blockuser2);
        expect(expectedResult).toEqual([blockuser, blockuser2]);
      });

      it('should accept null and undefined values', () => {
        const blockuser: IBlockuser = sampleWithRequiredData;
        expectedResult = service.addBlockuserToCollectionIfMissing([], null, blockuser, undefined);
        expect(expectedResult).toEqual([blockuser]);
      });

      it('should return initial array if no Blockuser is added', () => {
        const blockuserCollection: IBlockuser[] = [sampleWithRequiredData];
        expectedResult = service.addBlockuserToCollectionIfMissing(blockuserCollection, undefined, null);
        expect(expectedResult).toEqual(blockuserCollection);
      });
    });

    describe('compareBlockuser', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBlockuser(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 27219 };
        const entity2 = null;

        const compareResult1 = service.compareBlockuser(entity1, entity2);
        const compareResult2 = service.compareBlockuser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 27219 };
        const entity2 = { id: 25341 };

        const compareResult1 = service.compareBlockuser(entity1, entity2);
        const compareResult2 = service.compareBlockuser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 27219 };
        const entity2 = { id: 27219 };

        const compareResult1 = service.compareBlockuser(entity1, entity2);
        const compareResult2 = service.compareBlockuser(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
