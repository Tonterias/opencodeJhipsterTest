import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IConfigVariables } from '../config-variables.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../config-variables.test-samples';

import { ConfigVariablesService } from './config-variables.service';

const requireRestSample: IConfigVariables = {
  ...sampleWithRequiredData,
};

describe('ConfigVariables Service', () => {
  let service: ConfigVariablesService;
  let httpMock: HttpTestingController;
  let expectedResult: IConfigVariables | IConfigVariables[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ConfigVariablesService);
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

    it('should create a ConfigVariables', () => {
      const configVariables = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(configVariables).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ConfigVariables', () => {
      const configVariables = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(configVariables).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ConfigVariables', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ConfigVariables', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ConfigVariables', () => {
      service.delete(123).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addConfigVariablesToCollectionIfMissing', () => {
      it('should add a ConfigVariables to an empty array', () => {
        const configVariables: IConfigVariables = sampleWithRequiredData;
        expectedResult = service.addConfigVariablesToCollectionIfMissing([], configVariables);
        expect(expectedResult).toEqual([configVariables]);
      });

      it('should not add a ConfigVariables to an array that contains it', () => {
        const configVariables: IConfigVariables = sampleWithRequiredData;
        const configVariablesCollection: IConfigVariables[] = [
          {
            ...configVariables,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addConfigVariablesToCollectionIfMissing(configVariablesCollection, configVariables);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ConfigVariables to an array that doesn't contain it", () => {
        const configVariables: IConfigVariables = sampleWithRequiredData;
        const configVariablesCollection: IConfigVariables[] = [sampleWithPartialData];
        expectedResult = service.addConfigVariablesToCollectionIfMissing(configVariablesCollection, configVariables);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(configVariables);
      });

      it('should add only unique ConfigVariables to an array', () => {
        const configVariablesArray: IConfigVariables[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const configVariablesCollection: IConfigVariables[] = [sampleWithRequiredData];
        expectedResult = service.addConfigVariablesToCollectionIfMissing(configVariablesCollection, ...configVariablesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const configVariables: IConfigVariables = sampleWithRequiredData;
        const configVariables2: IConfigVariables = sampleWithPartialData;
        expectedResult = service.addConfigVariablesToCollectionIfMissing([], configVariables, configVariables2);
        expect(expectedResult).toEqual([configVariables, configVariables2]);
      });

      it('should accept null and undefined values', () => {
        const configVariables: IConfigVariables = sampleWithRequiredData;
        expectedResult = service.addConfigVariablesToCollectionIfMissing([], null, configVariables, undefined);
        expect(expectedResult).toEqual([configVariables]);
      });

      it('should return initial array if no ConfigVariables is added', () => {
        const configVariablesCollection: IConfigVariables[] = [sampleWithRequiredData];
        expectedResult = service.addConfigVariablesToCollectionIfMissing(configVariablesCollection, undefined, null);
        expect(expectedResult).toEqual(configVariablesCollection);
      });
    });

    describe('compareConfigVariables', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareConfigVariables(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 13986 };
        const entity2 = null;

        const compareResult1 = service.compareConfigVariables(entity1, entity2);
        const compareResult2 = service.compareConfigVariables(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 13986 };
        const entity2 = { id: 5001 };

        const compareResult1 = service.compareConfigVariables(entity1, entity2);
        const compareResult2 = service.compareConfigVariables(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 13986 };
        const entity2 = { id: 13986 };

        const compareResult1 = service.compareConfigVariables(entity1, entity2);
        const compareResult2 = service.compareConfigVariables(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
