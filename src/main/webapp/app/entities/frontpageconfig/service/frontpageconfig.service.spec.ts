import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IFrontpageconfig } from '../frontpageconfig.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../frontpageconfig.test-samples';

import { FrontpageconfigService, RestFrontpageconfig } from './frontpageconfig.service';

const requireRestSample: RestFrontpageconfig = {
  ...sampleWithRequiredData,
  creationDate: sampleWithRequiredData.creationDate?.toJSON(),
};

describe('Frontpageconfig Service', () => {
  let service: FrontpageconfigService;
  let httpMock: HttpTestingController;
  let expectedResult: IFrontpageconfig | IFrontpageconfig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FrontpageconfigService);
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

    it('should create a Frontpageconfig', () => {
      const frontpageconfig = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(frontpageconfig).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Frontpageconfig', () => {
      const frontpageconfig = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(frontpageconfig).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Frontpageconfig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Frontpageconfig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Frontpageconfig', () => {
      service.delete(123).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addFrontpageconfigToCollectionIfMissing', () => {
      it('should add a Frontpageconfig to an empty array', () => {
        const frontpageconfig: IFrontpageconfig = sampleWithRequiredData;
        expectedResult = service.addFrontpageconfigToCollectionIfMissing([], frontpageconfig);
        expect(expectedResult).toEqual([frontpageconfig]);
      });

      it('should not add a Frontpageconfig to an array that contains it', () => {
        const frontpageconfig: IFrontpageconfig = sampleWithRequiredData;
        const frontpageconfigCollection: IFrontpageconfig[] = [
          {
            ...frontpageconfig,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFrontpageconfigToCollectionIfMissing(frontpageconfigCollection, frontpageconfig);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Frontpageconfig to an array that doesn't contain it", () => {
        const frontpageconfig: IFrontpageconfig = sampleWithRequiredData;
        const frontpageconfigCollection: IFrontpageconfig[] = [sampleWithPartialData];
        expectedResult = service.addFrontpageconfigToCollectionIfMissing(frontpageconfigCollection, frontpageconfig);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(frontpageconfig);
      });

      it('should add only unique Frontpageconfig to an array', () => {
        const frontpageconfigArray: IFrontpageconfig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const frontpageconfigCollection: IFrontpageconfig[] = [sampleWithRequiredData];
        expectedResult = service.addFrontpageconfigToCollectionIfMissing(frontpageconfigCollection, ...frontpageconfigArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const frontpageconfig: IFrontpageconfig = sampleWithRequiredData;
        const frontpageconfig2: IFrontpageconfig = sampleWithPartialData;
        expectedResult = service.addFrontpageconfigToCollectionIfMissing([], frontpageconfig, frontpageconfig2);
        expect(expectedResult).toEqual([frontpageconfig, frontpageconfig2]);
      });

      it('should accept null and undefined values', () => {
        const frontpageconfig: IFrontpageconfig = sampleWithRequiredData;
        expectedResult = service.addFrontpageconfigToCollectionIfMissing([], null, frontpageconfig, undefined);
        expect(expectedResult).toEqual([frontpageconfig]);
      });

      it('should return initial array if no Frontpageconfig is added', () => {
        const frontpageconfigCollection: IFrontpageconfig[] = [sampleWithRequiredData];
        expectedResult = service.addFrontpageconfigToCollectionIfMissing(frontpageconfigCollection, undefined, null);
        expect(expectedResult).toEqual(frontpageconfigCollection);
      });
    });

    describe('compareFrontpageconfig', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFrontpageconfig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 17235 };
        const entity2 = null;

        const compareResult1 = service.compareFrontpageconfig(entity1, entity2);
        const compareResult2 = service.compareFrontpageconfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 17235 };
        const entity2 = { id: 24940 };

        const compareResult1 = service.compareFrontpageconfig(entity1, entity2);
        const compareResult2 = service.compareFrontpageconfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 17235 };
        const entity2 = { id: 17235 };

        const compareResult1 = service.compareFrontpageconfig(entity1, entity2);
        const compareResult2 = service.compareFrontpageconfig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
