import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ICommunity } from '../community.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../community.test-samples';

import { CommunityService, RestCommunity } from './community.service';

const requireRestSample: RestCommunity = {
  ...sampleWithRequiredData,
  creationDate: sampleWithRequiredData.creationDate?.toJSON(),
};

describe('Community Service', () => {
  let service: CommunityService;
  let httpMock: HttpTestingController;
  let expectedResult: ICommunity | ICommunity[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CommunityService);
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

    it('should create a Community', () => {
      const community = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(community).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Community', () => {
      const community = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(community).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Community', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Community', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Community', () => {
      service.delete(123).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('addCommunityToCollectionIfMissing', () => {
      it('should add a Community to an empty array', () => {
        const community: ICommunity = sampleWithRequiredData;
        expectedResult = service.addCommunityToCollectionIfMissing([], community);
        expect(expectedResult).toEqual([community]);
      });

      it('should not add a Community to an array that contains it', () => {
        const community: ICommunity = sampleWithRequiredData;
        const communityCollection: ICommunity[] = [
          {
            ...community,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCommunityToCollectionIfMissing(communityCollection, community);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Community to an array that doesn't contain it", () => {
        const community: ICommunity = sampleWithRequiredData;
        const communityCollection: ICommunity[] = [sampleWithPartialData];
        expectedResult = service.addCommunityToCollectionIfMissing(communityCollection, community);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(community);
      });

      it('should add only unique Community to an array', () => {
        const communityArray: ICommunity[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const communityCollection: ICommunity[] = [sampleWithRequiredData];
        expectedResult = service.addCommunityToCollectionIfMissing(communityCollection, ...communityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const community: ICommunity = sampleWithRequiredData;
        const community2: ICommunity = sampleWithPartialData;
        expectedResult = service.addCommunityToCollectionIfMissing([], community, community2);
        expect(expectedResult).toEqual([community, community2]);
      });

      it('should accept null and undefined values', () => {
        const community: ICommunity = sampleWithRequiredData;
        expectedResult = service.addCommunityToCollectionIfMissing([], null, community, undefined);
        expect(expectedResult).toEqual([community]);
      });

      it('should return initial array if no Community is added', () => {
        const communityCollection: ICommunity[] = [sampleWithRequiredData];
        expectedResult = service.addCommunityToCollectionIfMissing(communityCollection, undefined, null);
        expect(expectedResult).toEqual(communityCollection);
      });
    });

    describe('compareCommunity', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCommunity(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 32684 };
        const entity2 = null;

        const compareResult1 = service.compareCommunity(entity1, entity2);
        const compareResult2 = service.compareCommunity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 32684 };
        const entity2 = { id: 25732 };

        const compareResult1 = service.compareCommunity(entity1, entity2);
        const compareResult2 = service.compareCommunity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 32684 };
        const entity2 = { id: 32684 };

        const compareResult1 = service.compareCommunity(entity1, entity2);
        const compareResult2 = service.compareCommunity(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
