import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IUrllink } from '../urllink.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../urllink.test-samples';

import { UrllinkService } from './urllink.service';

const requireRestSample: IUrllink = {
  ...sampleWithRequiredData,
};

describe('Urllink Service', () => {
  let service: UrllinkService;
  let httpMock: HttpTestingController;
  let expectedResult: IUrllink | IUrllink[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(UrllinkService);
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

    it('should create a Urllink', () => {
      const urllink = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(urllink).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Urllink', () => {
      const urllink = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(urllink).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Urllink', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Urllink', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Urllink', () => {
      service.delete(123).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addUrllinkToCollectionIfMissing', () => {
      it('should add a Urllink to an empty array', () => {
        const urllink: IUrllink = sampleWithRequiredData;
        expectedResult = service.addUrllinkToCollectionIfMissing([], urllink);
        expect(expectedResult).toEqual([urllink]);
      });

      it('should not add a Urllink to an array that contains it', () => {
        const urllink: IUrllink = sampleWithRequiredData;
        const urllinkCollection: IUrllink[] = [
          {
            ...urllink,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUrllinkToCollectionIfMissing(urllinkCollection, urllink);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Urllink to an array that doesn't contain it", () => {
        const urllink: IUrllink = sampleWithRequiredData;
        const urllinkCollection: IUrllink[] = [sampleWithPartialData];
        expectedResult = service.addUrllinkToCollectionIfMissing(urllinkCollection, urllink);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(urllink);
      });

      it('should add only unique Urllink to an array', () => {
        const urllinkArray: IUrllink[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const urllinkCollection: IUrllink[] = [sampleWithRequiredData];
        expectedResult = service.addUrllinkToCollectionIfMissing(urllinkCollection, ...urllinkArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const urllink: IUrllink = sampleWithRequiredData;
        const urllink2: IUrllink = sampleWithPartialData;
        expectedResult = service.addUrllinkToCollectionIfMissing([], urllink, urllink2);
        expect(expectedResult).toEqual([urllink, urllink2]);
      });

      it('should accept null and undefined values', () => {
        const urllink: IUrllink = sampleWithRequiredData;
        expectedResult = service.addUrllinkToCollectionIfMissing([], null, urllink, undefined);
        expect(expectedResult).toEqual([urllink]);
      });

      it('should return initial array if no Urllink is added', () => {
        const urllinkCollection: IUrllink[] = [sampleWithRequiredData];
        expectedResult = service.addUrllinkToCollectionIfMissing(urllinkCollection, undefined, null);
        expect(expectedResult).toEqual(urllinkCollection);
      });
    });

    describe('compareUrllink', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUrllink(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 16948 };
        const entity2 = null;

        const compareResult1 = service.compareUrllink(entity1, entity2);
        const compareResult2 = service.compareUrllink(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 16948 };
        const entity2 = { id: 20441 };

        const compareResult1 = service.compareUrllink(entity1, entity2);
        const compareResult2 = service.compareUrllink(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 16948 };
        const entity2 = { id: 16948 };

        const compareResult1 = service.compareUrllink(entity1, entity2);
        const compareResult2 = service.compareUrllink(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
