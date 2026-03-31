import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICeleb } from '../celeb.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../celeb.test-samples';

import { CelebService } from './celeb.service';

const requireRestSample: ICeleb = {
  ...sampleWithRequiredData,
};

describe('Celeb Service', () => {
  let service: CelebService;
  let httpMock: HttpTestingController;
  let expectedResult: ICeleb | ICeleb[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CelebService);
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

    it('should create a Celeb', () => {
      const celeb = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(celeb).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Celeb', () => {
      const celeb = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(celeb).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Celeb', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Celeb', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Celeb', () => {
      service.delete(123).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCelebToCollectionIfMissing', () => {
      it('should add a Celeb to an empty array', () => {
        const celeb: ICeleb = sampleWithRequiredData;
        expectedResult = service.addCelebToCollectionIfMissing([], celeb);
        expect(expectedResult).toEqual([celeb]);
      });

      it('should not add a Celeb to an array that contains it', () => {
        const celeb: ICeleb = sampleWithRequiredData;
        const celebCollection: ICeleb[] = [
          {
            ...celeb,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCelebToCollectionIfMissing(celebCollection, celeb);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Celeb to an array that doesn't contain it", () => {
        const celeb: ICeleb = sampleWithRequiredData;
        const celebCollection: ICeleb[] = [sampleWithPartialData];
        expectedResult = service.addCelebToCollectionIfMissing(celebCollection, celeb);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(celeb);
      });

      it('should add only unique Celeb to an array', () => {
        const celebArray: ICeleb[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const celebCollection: ICeleb[] = [sampleWithRequiredData];
        expectedResult = service.addCelebToCollectionIfMissing(celebCollection, ...celebArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const celeb: ICeleb = sampleWithRequiredData;
        const celeb2: ICeleb = sampleWithPartialData;
        expectedResult = service.addCelebToCollectionIfMissing([], celeb, celeb2);
        expect(expectedResult).toEqual([celeb, celeb2]);
      });

      it('should accept null and undefined values', () => {
        const celeb: ICeleb = sampleWithRequiredData;
        expectedResult = service.addCelebToCollectionIfMissing([], null, celeb, undefined);
        expect(expectedResult).toEqual([celeb]);
      });

      it('should return initial array if no Celeb is added', () => {
        const celebCollection: ICeleb[] = [sampleWithRequiredData];
        expectedResult = service.addCelebToCollectionIfMissing(celebCollection, undefined, null);
        expect(expectedResult).toEqual(celebCollection);
      });
    });

    describe('compareCeleb', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCeleb(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 24101 };
        const entity2 = null;

        const compareResult1 = service.compareCeleb(entity1, entity2);
        const compareResult2 = service.compareCeleb(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 24101 };
        const entity2 = { id: 26548 };

        const compareResult1 = service.compareCeleb(entity1, entity2);
        const compareResult2 = service.compareCeleb(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 24101 };
        const entity2 = { id: 24101 };

        const compareResult1 = service.compareCeleb(entity1, entity2);
        const compareResult2 = service.compareCeleb(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
