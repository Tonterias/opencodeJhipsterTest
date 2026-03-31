import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IUrllink, NewUrllink } from '../urllink.model';

export type PartialUpdateUrllink = Partial<IUrllink> & Pick<IUrllink, 'id'>;

@Injectable()
export class UrllinksService {
  readonly urllinksParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly urllinksResource = httpResource<IUrllink[]>(() => {
    const params = this.urllinksParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of urllink that have been fetched. It is updated when the urllinksResource emits a new value.
   * In case of error while fetching the urllinks, the signal is set to an empty array.
   */
  readonly urllinks = computed(() => (this.urllinksResource.hasValue() ? this.urllinksResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/urllinks');
}

@Injectable({ providedIn: 'root' })
export class UrllinkService extends UrllinksService {
  protected readonly http = inject(HttpClient);

  create(urllink: NewUrllink): Observable<IUrllink> {
    return this.http.post<IUrllink>(this.resourceUrl, urllink);
  }

  update(urllink: IUrllink): Observable<IUrllink> {
    return this.http.put<IUrllink>(`${this.resourceUrl}/${encodeURIComponent(this.getUrllinkIdentifier(urllink))}`, urllink);
  }

  partialUpdate(urllink: PartialUpdateUrllink): Observable<IUrllink> {
    return this.http.patch<IUrllink>(`${this.resourceUrl}/${encodeURIComponent(this.getUrllinkIdentifier(urllink))}`, urllink);
  }

  find(id: number): Observable<IUrllink> {
    return this.http.get<IUrllink>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<IUrllink[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUrllink[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getUrllinkIdentifier(urllink: Pick<IUrllink, 'id'>): number {
    return urllink.id;
  }

  compareUrllink(o1: Pick<IUrllink, 'id'> | null, o2: Pick<IUrllink, 'id'> | null): boolean {
    return o1 && o2 ? this.getUrllinkIdentifier(o1) === this.getUrllinkIdentifier(o2) : o1 === o2;
  }

  addUrllinkToCollectionIfMissing<Type extends Pick<IUrllink, 'id'>>(
    urllinkCollection: Type[],
    ...urllinksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const urllinks: Type[] = urllinksToCheck.filter(isPresent);
    if (urllinks.length > 0) {
      const urllinkCollectionIdentifiers = urllinkCollection.map(urllinkItem => this.getUrllinkIdentifier(urllinkItem));
      const urllinksToAdd = urllinks.filter(urllinkItem => {
        const urllinkIdentifier = this.getUrllinkIdentifier(urllinkItem);
        if (urllinkCollectionIdentifiers.includes(urllinkIdentifier)) {
          return false;
        }
        urllinkCollectionIdentifiers.push(urllinkIdentifier);
        return true;
      });
      return [...urllinksToAdd, ...urllinkCollection];
    }
    return urllinkCollection;
  }
}
