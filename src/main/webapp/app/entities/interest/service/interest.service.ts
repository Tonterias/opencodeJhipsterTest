import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IInterest, NewInterest } from '../interest.model';

export type PartialUpdateInterest = Partial<IInterest> & Pick<IInterest, 'id'>;

@Injectable()
export class InterestsService {
  readonly interestsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly interestsResource = httpResource<IInterest[]>(() => {
    const params = this.interestsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of interest that have been fetched. It is updated when the interestsResource emits a new value.
   * In case of error while fetching the interests, the signal is set to an empty array.
   */
  readonly interests = computed(() => (this.interestsResource.hasValue() ? this.interestsResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/interests');
}

@Injectable({ providedIn: 'root' })
export class InterestService extends InterestsService {
  protected readonly http = inject(HttpClient);

  create(interest: NewInterest): Observable<IInterest> {
    return this.http.post<IInterest>(this.resourceUrl, interest);
  }

  update(interest: IInterest): Observable<IInterest> {
    return this.http.put<IInterest>(`${this.resourceUrl}/${encodeURIComponent(this.getInterestIdentifier(interest))}`, interest);
  }

  partialUpdate(interest: PartialUpdateInterest): Observable<IInterest> {
    return this.http.patch<IInterest>(`${this.resourceUrl}/${encodeURIComponent(this.getInterestIdentifier(interest))}`, interest);
  }

  find(id: number): Observable<IInterest> {
    return this.http.get<IInterest>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<IInterest[]>> {
    const options = createRequestOption(req);
    return this.http.get<IInterest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getInterestIdentifier(interest: Pick<IInterest, 'id'>): number {
    return interest.id;
  }

  compareInterest(o1: Pick<IInterest, 'id'> | null, o2: Pick<IInterest, 'id'> | null): boolean {
    return o1 && o2 ? this.getInterestIdentifier(o1) === this.getInterestIdentifier(o2) : o1 === o2;
  }

  addInterestToCollectionIfMissing<Type extends Pick<IInterest, 'id'>>(
    interestCollection: Type[],
    ...interestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const interests: Type[] = interestsToCheck.filter(isPresent);
    if (interests.length > 0) {
      const interestCollectionIdentifiers = interestCollection.map(interestItem => this.getInterestIdentifier(interestItem));
      const interestsToAdd = interests.filter(interestItem => {
        const interestIdentifier = this.getInterestIdentifier(interestItem);
        if (interestCollectionIdentifiers.includes(interestIdentifier)) {
          return false;
        }
        interestCollectionIdentifiers.push(interestIdentifier);
        return true;
      });
      return [...interestsToAdd, ...interestCollection];
    }
    return interestCollection;
  }
}
