import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICeleb, NewCeleb } from '../celeb.model';

export type PartialUpdateCeleb = Partial<ICeleb> & Pick<ICeleb, 'id'>;

@Injectable()
export class CelebsService {
  readonly celebsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(undefined);
  readonly celebsResource = httpResource<ICeleb[]>(() => {
    const params = this.celebsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of celeb that have been fetched. It is updated when the celebsResource emits a new value.
   * In case of error while fetching the celebs, the signal is set to an empty array.
   */
  readonly celebs = computed(() => (this.celebsResource.hasValue() ? this.celebsResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/celebs');
}

@Injectable({ providedIn: 'root' })
export class CelebService extends CelebsService {
  protected readonly http = inject(HttpClient);

  create(celeb: NewCeleb): Observable<ICeleb> {
    return this.http.post<ICeleb>(this.resourceUrl, celeb);
  }

  update(celeb: ICeleb): Observable<ICeleb> {
    return this.http.put<ICeleb>(`${this.resourceUrl}/${encodeURIComponent(this.getCelebIdentifier(celeb))}`, celeb);
  }

  partialUpdate(celeb: PartialUpdateCeleb): Observable<ICeleb> {
    return this.http.patch<ICeleb>(`${this.resourceUrl}/${encodeURIComponent(this.getCelebIdentifier(celeb))}`, celeb);
  }

  find(id: number): Observable<ICeleb> {
    return this.http.get<ICeleb>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<ICeleb[]>> {
    const options = createRequestOption(req);
    return this.http.get<ICeleb[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getCelebIdentifier(celeb: Pick<ICeleb, 'id'>): number {
    return celeb.id;
  }

  compareCeleb(o1: Pick<ICeleb, 'id'> | null, o2: Pick<ICeleb, 'id'> | null): boolean {
    return o1 && o2 ? this.getCelebIdentifier(o1) === this.getCelebIdentifier(o2) : o1 === o2;
  }

  addCelebToCollectionIfMissing<Type extends Pick<ICeleb, 'id'>>(
    celebCollection: Type[],
    ...celebsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const celebs: Type[] = celebsToCheck.filter(isPresent);
    if (celebs.length > 0) {
      const celebCollectionIdentifiers = celebCollection.map(celebItem => this.getCelebIdentifier(celebItem));
      const celebsToAdd = celebs.filter(celebItem => {
        const celebIdentifier = this.getCelebIdentifier(celebItem);
        if (celebCollectionIdentifiers.includes(celebIdentifier)) {
          return false;
        }
        celebCollectionIdentifiers.push(celebIdentifier);
        return true;
      });
      return [...celebsToAdd, ...celebCollection];
    }
    return celebCollection;
  }
}
