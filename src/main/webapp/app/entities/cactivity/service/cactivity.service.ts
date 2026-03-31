import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICactivity, NewCactivity } from '../cactivity.model';

export type PartialUpdateCactivity = Partial<ICactivity> & Pick<ICactivity, 'id'>;

@Injectable()
export class CactivitiesService {
  readonly cactivitiesParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly cactivitiesResource = httpResource<ICactivity[]>(() => {
    const params = this.cactivitiesParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cactivity that have been fetched. It is updated when the cactivitiesResource emits a new value.
   * In case of error while fetching the cactivities, the signal is set to an empty array.
   */
  readonly cactivities = computed(() => (this.cactivitiesResource.hasValue() ? this.cactivitiesResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cactivities');
}

@Injectable({ providedIn: 'root' })
export class CactivityService extends CactivitiesService {
  protected readonly http = inject(HttpClient);

  create(cactivity: NewCactivity): Observable<ICactivity> {
    return this.http.post<ICactivity>(this.resourceUrl, cactivity);
  }

  update(cactivity: ICactivity): Observable<ICactivity> {
    return this.http.put<ICactivity>(`${this.resourceUrl}/${encodeURIComponent(this.getCactivityIdentifier(cactivity))}`, cactivity);
  }

  partialUpdate(cactivity: PartialUpdateCactivity): Observable<ICactivity> {
    return this.http.patch<ICactivity>(`${this.resourceUrl}/${encodeURIComponent(this.getCactivityIdentifier(cactivity))}`, cactivity);
  }

  find(id: number): Observable<ICactivity> {
    return this.http.get<ICactivity>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<ICactivity[]>> {
    const options = createRequestOption(req);
    return this.http.get<ICactivity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getCactivityIdentifier(cactivity: Pick<ICactivity, 'id'>): number {
    return cactivity.id;
  }

  compareCactivity(o1: Pick<ICactivity, 'id'> | null, o2: Pick<ICactivity, 'id'> | null): boolean {
    return o1 && o2 ? this.getCactivityIdentifier(o1) === this.getCactivityIdentifier(o2) : o1 === o2;
  }

  addCactivityToCollectionIfMissing<Type extends Pick<ICactivity, 'id'>>(
    cactivityCollection: Type[],
    ...cactivitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cactivities: Type[] = cactivitiesToCheck.filter(isPresent);
    if (cactivities.length > 0) {
      const cactivityCollectionIdentifiers = cactivityCollection.map(cactivityItem => this.getCactivityIdentifier(cactivityItem));
      const cactivitiesToAdd = cactivities.filter(cactivityItem => {
        const cactivityIdentifier = this.getCactivityIdentifier(cactivityItem);
        if (cactivityCollectionIdentifiers.includes(cactivityIdentifier)) {
          return false;
        }
        cactivityCollectionIdentifiers.push(cactivityIdentifier);
        return true;
      });
      return [...cactivitiesToAdd, ...cactivityCollection];
    }
    return cactivityCollection;
  }
}
