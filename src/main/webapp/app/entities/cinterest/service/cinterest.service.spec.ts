import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICinterest } from '../cinterest.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../cinterest.test-samples';

import { CinterestService } from './cinterest.service';

const requireRestSample: ICinterest = {
  ...sampleWithRequiredData,
};

describe('Cinterest Service', () => {
  let service: CinterestService;
  let httpMock: HttpTestingController;
  let expectedResult: ICinterest | ICinterest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CinterestService);
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

    it('should create a Cinterest', () => {
      const cinterest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cinterest).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Cinterest', () => {
      const cinterest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cinterest).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Cinterest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Cinterest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Cinterest', () => {
      service.delete(123).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCinterestToCollectionIfMissing', () => {
      it('should add a Cinterest to an empty array', () => {
        const cinterest: ICinterest = sampleWithRequiredData;
        expectedResult = service.addCinterestToCollectionIfMissing([], cinterest);
        expect(expectedResult).toEqual([cinterest]);
      });

      it('should not add a Cinterest to an array that contains it', () => {
        const cinterest: ICinterest = sampleWithRequiredData;
        const cinterestCollection: ICinterest[] = [
          {
            ...cinterest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCinterestToCollectionIfMissing(cinterestCollection, cinterest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Cinterest to an array that doesn't contain it", () => {
        const cinterest: ICinterest = sampleWithRequiredData;
        const cinterestCollection: ICinterest[] = [sampleWithPartialData];
        expectedResult = service.addCinterestToCollectionIfMissing(cinterestCollection, cinterest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cinterest);
      });

      it('should add only unique Cinterest to an array', () => {
        const cinterestArray: ICinterest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cinterestCollection: ICinterest[] = [sampleWithRequiredData];
        expectedResult = service.addCinterestToCollectionIfMissing(cinterestCollection, ...cinterestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cinterest: ICinterest = sampleWithRequiredData;
        const cinterest2: ICinterest = sampleWithPartialData;
        expectedResult = service.addCinterestToCollectionIfMissing([], cinterest, cinterest2);
        expect(expectedResult).toEqual([cinterest, cinterest2]);
      });

      it('should accept null and undefined values', () => {
        const cinterest: ICinterest = sampleWithRequiredData;
        expectedResult = service.addCinterestToCollectionIfMissing([], null, cinterest, undefined);
        expect(expectedResult).toEqual([cinterest]);
      });

      it('should return initial array if no Cinterest is added', () => {
        const cinterestCollection: ICinterest[] = [sampleWithRequiredData];
        expectedResult = service.addCinterestToCollectionIfMissing(cinterestCollection, undefined, null);
        expect(expectedResult).toEqual(cinterestCollection);
      });
    });

    describe('compareCinterest', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCinterest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 11835 };
        const entity2 = null;

        const compareResult1 = service.compareCinterest(entity1, entity2);
        const compareResult2 = service.compareCinterest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 11835 };
        const entity2 = { id: 31193 };

        const compareResult1 = service.compareCinterest(entity1, entity2);
        const compareResult2 = service.compareCinterest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 11835 };
        const entity2 = { id: 11835 };

        const compareResult1 = service.compareCinterest(entity1, entity2);
        const compareResult2 = service.compareCinterest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
