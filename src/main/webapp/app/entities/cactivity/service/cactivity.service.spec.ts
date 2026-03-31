import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICactivity } from '../cactivity.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../cactivity.test-samples';

import { CactivityService } from './cactivity.service';

const requireRestSample: ICactivity = {
  ...sampleWithRequiredData,
};

describe('Cactivity Service', () => {
  let service: CactivityService;
  let httpMock: HttpTestingController;
  let expectedResult: ICactivity | ICactivity[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CactivityService);
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

    it('should create a Cactivity', () => {
      const cactivity = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cactivity).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Cactivity', () => {
      const cactivity = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cactivity).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Cactivity', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Cactivity', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Cactivity', () => {
      service.delete(123).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCactivityToCollectionIfMissing', () => {
      it('should add a Cactivity to an empty array', () => {
        const cactivity: ICactivity = sampleWithRequiredData;
        expectedResult = service.addCactivityToCollectionIfMissing([], cactivity);
        expect(expectedResult).toEqual([cactivity]);
      });

      it('should not add a Cactivity to an array that contains it', () => {
        const cactivity: ICactivity = sampleWithRequiredData;
        const cactivityCollection: ICactivity[] = [
          {
            ...cactivity,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCactivityToCollectionIfMissing(cactivityCollection, cactivity);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Cactivity to an array that doesn't contain it", () => {
        const cactivity: ICactivity = sampleWithRequiredData;
        const cactivityCollection: ICactivity[] = [sampleWithPartialData];
        expectedResult = service.addCactivityToCollectionIfMissing(cactivityCollection, cactivity);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cactivity);
      });

      it('should add only unique Cactivity to an array', () => {
        const cactivityArray: ICactivity[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cactivityCollection: ICactivity[] = [sampleWithRequiredData];
        expectedResult = service.addCactivityToCollectionIfMissing(cactivityCollection, ...cactivityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cactivity: ICactivity = sampleWithRequiredData;
        const cactivity2: ICactivity = sampleWithPartialData;
        expectedResult = service.addCactivityToCollectionIfMissing([], cactivity, cactivity2);
        expect(expectedResult).toEqual([cactivity, cactivity2]);
      });

      it('should accept null and undefined values', () => {
        const cactivity: ICactivity = sampleWithRequiredData;
        expectedResult = service.addCactivityToCollectionIfMissing([], null, cactivity, undefined);
        expect(expectedResult).toEqual([cactivity]);
      });

      it('should return initial array if no Cactivity is added', () => {
        const cactivityCollection: ICactivity[] = [sampleWithRequiredData];
        expectedResult = service.addCactivityToCollectionIfMissing(cactivityCollection, undefined, null);
        expect(expectedResult).toEqual(cactivityCollection);
      });
    });

    describe('compareCactivity', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCactivity(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 25738 };
        const entity2 = null;

        const compareResult1 = service.compareCactivity(entity1, entity2);
        const compareResult2 = service.compareCactivity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 25738 };
        const entity2 = { id: 15121 };

        const compareResult1 = service.compareCactivity(entity1, entity2);
        const compareResult2 = service.compareCactivity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 25738 };
        const entity2 = { id: 25738 };

        const compareResult1 = service.compareCactivity(entity1, entity2);
        const compareResult2 = service.compareCactivity(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
