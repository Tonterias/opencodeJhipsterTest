import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICceleb, NewCceleb } from '../cceleb.model';

export type PartialUpdateCceleb = Partial<ICceleb> & Pick<ICceleb, 'id'>;

@Injectable()
export class CcelebsService {
  readonly ccelebsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly ccelebsResource = httpResource<ICceleb[]>(() => {
    const params = this.ccelebsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cceleb that have been fetched. It is updated when the ccelebsResource emits a new value.
   * In case of error while fetching the ccelebs, the signal is set to an empty array.
   */
  readonly ccelebs = computed(() => (this.ccelebsResource.hasValue() ? this.ccelebsResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/ccelebs');
}

@Injectable({ providedIn: 'root' })
export class CcelebService extends CcelebsService {
  protected readonly http = inject(HttpClient);

  create(cceleb: NewCceleb): Observable<ICceleb> {
    return this.http.post<ICceleb>(this.resourceUrl, cceleb);
  }

  update(cceleb: ICceleb): Observable<ICceleb> {
    return this.http.put<ICceleb>(`${this.resourceUrl}/${encodeURIComponent(this.getCcelebIdentifier(cceleb))}`, cceleb);
  }

  partialUpdate(cceleb: PartialUpdateCceleb): Observable<ICceleb> {
    return this.http.patch<ICceleb>(`${this.resourceUrl}/${encodeURIComponent(this.getCcelebIdentifier(cceleb))}`, cceleb);
  }

  find(id: number): Observable<ICceleb> {
    return this.http.get<ICceleb>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<ICceleb[]>> {
    const options = createRequestOption(req);
    return this.http.get<ICceleb[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getCcelebIdentifier(cceleb: Pick<ICceleb, 'id'>): number {
    return cceleb.id;
  }

  compareCceleb(o1: Pick<ICceleb, 'id'> | null, o2: Pick<ICceleb, 'id'> | null): boolean {
    return o1 && o2 ? this.getCcelebIdentifier(o1) === this.getCcelebIdentifier(o2) : o1 === o2;
  }

  addCcelebToCollectionIfMissing<Type extends Pick<ICceleb, 'id'>>(
    ccelebCollection: Type[],
    ...ccelebsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ccelebs: Type[] = ccelebsToCheck.filter(isPresent);
    if (ccelebs.length > 0) {
      const ccelebCollectionIdentifiers = ccelebCollection.map(ccelebItem => this.getCcelebIdentifier(ccelebItem));
      const ccelebsToAdd = ccelebs.filter(ccelebItem => {
        const ccelebIdentifier = this.getCcelebIdentifier(ccelebItem);
        if (ccelebCollectionIdentifiers.includes(ccelebIdentifier)) {
          return false;
        }
        ccelebCollectionIdentifiers.push(ccelebIdentifier);
        return true;
      });
      return [...ccelebsToAdd, ...ccelebCollection];
    }
    return ccelebCollection;
  }
}
