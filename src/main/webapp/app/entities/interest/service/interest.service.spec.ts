import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IInterest } from '../interest.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../interest.test-samples';

import { InterestService } from './interest.service';

const requireRestSample: IInterest = {
  ...sampleWithRequiredData,
};

describe('Interest Service', () => {
  let service: InterestService;
  let httpMock: HttpTestingController;
  let expectedResult: IInterest | IInterest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(InterestService);
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

    it('should create a Interest', () => {
      const interest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(interest).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Interest', () => {
      const interest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(interest).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Interest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Interest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Interest', () => {
      service.delete(123).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addInterestToCollectionIfMissing', () => {
      it('should add a Interest to an empty array', () => {
        const interest: IInterest = sampleWithRequiredData;
        expectedResult = service.addInterestToCollectionIfMissing([], interest);
        expect(expectedResult).toEqual([interest]);
      });

      it('should not add a Interest to an array that contains it', () => {
        const interest: IInterest = sampleWithRequiredData;
        const interestCollection: IInterest[] = [
          {
            ...interest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInterestToCollectionIfMissing(interestCollection, interest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Interest to an array that doesn't contain it", () => {
        const interest: IInterest = sampleWithRequiredData;
        const interestCollection: IInterest[] = [sampleWithPartialData];
        expectedResult = service.addInterestToCollectionIfMissing(interestCollection, interest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interest);
      });

      it('should add only unique Interest to an array', () => {
        const interestArray: IInterest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const interestCollection: IInterest[] = [sampleWithRequiredData];
        expectedResult = service.addInterestToCollectionIfMissing(interestCollection, ...interestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const interest: IInterest = sampleWithRequiredData;
        const interest2: IInterest = sampleWithPartialData;
        expectedResult = service.addInterestToCollectionIfMissing([], interest, interest2);
        expect(expectedResult).toEqual([interest, interest2]);
      });

      it('should accept null and undefined values', () => {
        const interest: IInterest = sampleWithRequiredData;
        expectedResult = service.addInterestToCollectionIfMissing([], null, interest, undefined);
        expect(expectedResult).toEqual([interest]);
      });

      it('should return initial array if no Interest is added', () => {
        const interestCollection: IInterest[] = [sampleWithRequiredData];
        expectedResult = service.addInterestToCollectionIfMissing(interestCollection, undefined, null);
        expect(expectedResult).toEqual(interestCollection);
      });
    });

    describe('compareInterest', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInterest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 18212 };
        const entity2 = null;

        const compareResult1 = service.compareInterest(entity1, entity2);
        const compareResult2 = service.compareInterest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 18212 };
        const entity2 = { id: 5876 };

        const compareResult1 = service.compareInterest(entity1, entity2);
        const compareResult2 = service.compareInterest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 18212 };
        const entity2 = { id: 18212 };

        const compareResult1 = service.compareInterest(entity1, entity2);
        const compareResult2 = service.compareInterest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
