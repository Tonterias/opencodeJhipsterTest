import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICceleb } from '../cceleb.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../cceleb.test-samples';

import { CcelebService } from './cceleb.service';

const requireRestSample: ICceleb = {
  ...sampleWithRequiredData,
};

describe('Cceleb Service', () => {
  let service: CcelebService;
  let httpMock: HttpTestingController;
  let expectedResult: ICceleb | ICceleb[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CcelebService);
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

    it('should create a Cceleb', () => {
      const cceleb = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cceleb).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Cceleb', () => {
      const cceleb = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cceleb).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Cceleb', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Cceleb', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Cceleb', () => {
      service.delete(123).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCcelebToCollectionIfMissing', () => {
      it('should add a Cceleb to an empty array', () => {
        const cceleb: ICceleb = sampleWithRequiredData;
        expectedResult = service.addCcelebToCollectionIfMissing([], cceleb);
        expect(expectedResult).toEqual([cceleb]);
      });

      it('should not add a Cceleb to an array that contains it', () => {
        const cceleb: ICceleb = sampleWithRequiredData;
        const ccelebCollection: ICceleb[] = [
          {
            ...cceleb,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCcelebToCollectionIfMissing(ccelebCollection, cceleb);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Cceleb to an array that doesn't contain it", () => {
        const cceleb: ICceleb = sampleWithRequiredData;
        const ccelebCollection: ICceleb[] = [sampleWithPartialData];
        expectedResult = service.addCcelebToCollectionIfMissing(ccelebCollection, cceleb);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cceleb);
      });

      it('should add only unique Cceleb to an array', () => {
        const ccelebArray: ICceleb[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ccelebCollection: ICceleb[] = [sampleWithRequiredData];
        expectedResult = service.addCcelebToCollectionIfMissing(ccelebCollection, ...ccelebArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cceleb: ICceleb = sampleWithRequiredData;
        const cceleb2: ICceleb = sampleWithPartialData;
        expectedResult = service.addCcelebToCollectionIfMissing([], cceleb, cceleb2);
        expect(expectedResult).toEqual([cceleb, cceleb2]);
      });

      it('should accept null and undefined values', () => {
        const cceleb: ICceleb = sampleWithRequiredData;
        expectedResult = service.addCcelebToCollectionIfMissing([], null, cceleb, undefined);
        expect(expectedResult).toEqual([cceleb]);
      });

      it('should return initial array if no Cceleb is added', () => {
        const ccelebCollection: ICceleb[] = [sampleWithRequiredData];
        expectedResult = service.addCcelebToCollectionIfMissing(ccelebCollection, undefined, null);
        expect(expectedResult).toEqual(ccelebCollection);
      });
    });

    describe('compareCceleb', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCceleb(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 27907 };
        const entity2 = null;

        const compareResult1 = service.compareCceleb(entity1, entity2);
        const compareResult2 = service.compareCceleb(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 27907 };
        const entity2 = { id: 19336 };

        const compareResult1 = service.compareCceleb(entity1, entity2);
        const compareResult2 = service.compareCceleb(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 27907 };
        const entity2 = { id: 27907 };

        const compareResult1 = service.compareCceleb(entity1, entity2);
        const compareResult2 = service.compareCceleb(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
