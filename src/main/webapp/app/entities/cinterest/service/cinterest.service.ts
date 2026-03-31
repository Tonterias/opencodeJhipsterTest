import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICinterest, NewCinterest } from '../cinterest.model';

export type PartialUpdateCinterest = Partial<ICinterest> & Pick<ICinterest, 'id'>;

@Injectable()
export class CinterestsService {
  readonly cinterestsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly cinterestsResource = httpResource<ICinterest[]>(() => {
    const params = this.cinterestsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cinterest that have been fetched. It is updated when the cinterestsResource emits a new value.
   * In case of error while fetching the cinterests, the signal is set to an empty array.
   */
  readonly cinterests = computed(() => (this.cinterestsResource.hasValue() ? this.cinterestsResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cinterests');
}

@Injectable({ providedIn: 'root' })
export class CinterestService extends CinterestsService {
  protected readonly http = inject(HttpClient);

  create(cinterest: NewCinterest): Observable<ICinterest> {
    return this.http.post<ICinterest>(this.resourceUrl, cinterest);
  }

  update(cinterest: ICinterest): Observable<ICinterest> {
    return this.http.put<ICinterest>(`${this.resourceUrl}/${encodeURIComponent(this.getCinterestIdentifier(cinterest))}`, cinterest);
  }

  partialUpdate(cinterest: PartialUpdateCinterest): Observable<ICinterest> {
    return this.http.patch<ICinterest>(`${this.resourceUrl}/${encodeURIComponent(this.getCinterestIdentifier(cinterest))}`, cinterest);
  }

  find(id: number): Observable<ICinterest> {
    return this.http.get<ICinterest>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<ICinterest[]>> {
    const options = createRequestOption(req);
    return this.http.get<ICinterest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getCinterestIdentifier(cinterest: Pick<ICinterest, 'id'>): number {
    return cinterest.id;
  }

  compareCinterest(o1: Pick<ICinterest, 'id'> | null, o2: Pick<ICinterest, 'id'> | null): boolean {
    return o1 && o2 ? this.getCinterestIdentifier(o1) === this.getCinterestIdentifier(o2) : o1 === o2;
  }

  addCinterestToCollectionIfMissing<Type extends Pick<ICinterest, 'id'>>(
    cinterestCollection: Type[],
    ...cinterestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cinterests: Type[] = cinterestsToCheck.filter(isPresent);
    if (cinterests.length > 0) {
      const cinterestCollectionIdentifiers = cinterestCollection.map(cinterestItem => this.getCinterestIdentifier(cinterestItem));
      const cinterestsToAdd = cinterests.filter(cinterestItem => {
        const cinterestIdentifier = this.getCinterestIdentifier(cinterestItem);
        if (cinterestCollectionIdentifiers.includes(cinterestIdentifier)) {
          return false;
        }
        cinterestCollectionIdentifiers.push(cinterestIdentifier);
        return true;
      });
      return [...cinterestsToAdd, ...cinterestCollection];
    }
    return cinterestCollection;
  }
}
