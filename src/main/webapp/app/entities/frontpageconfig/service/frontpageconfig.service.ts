import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IFrontpageconfig, NewFrontpageconfig } from '../frontpageconfig.model';

export type PartialUpdateFrontpageconfig = Partial<IFrontpageconfig> & Pick<IFrontpageconfig, 'id'>;

type RestOf<T extends IFrontpageconfig | NewFrontpageconfig> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

export type RestFrontpageconfig = RestOf<IFrontpageconfig>;

export type NewRestFrontpageconfig = RestOf<NewFrontpageconfig>;

export type PartialUpdateRestFrontpageconfig = RestOf<PartialUpdateFrontpageconfig>;

@Injectable()
export class FrontpageconfigsService {
  readonly frontpageconfigsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly frontpageconfigsResource = httpResource<RestFrontpageconfig[]>(() => {
    const params = this.frontpageconfigsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of frontpageconfig that have been fetched. It is updated when the frontpageconfigsResource emits a new value.
   * In case of error while fetching the frontpageconfigs, the signal is set to an empty array.
   */
  readonly frontpageconfigs = computed(() =>
    (this.frontpageconfigsResource.hasValue() ? this.frontpageconfigsResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/frontpageconfigs');

  protected convertValueFromServer(restFrontpageconfig: RestFrontpageconfig): IFrontpageconfig {
    return {
      ...restFrontpageconfig,
      creationDate: restFrontpageconfig.creationDate ? dayjs(restFrontpageconfig.creationDate) : undefined,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class FrontpageconfigService extends FrontpageconfigsService {
  protected readonly http = inject(HttpClient);

  create(frontpageconfig: NewFrontpageconfig): Observable<IFrontpageconfig> {
    const copy = this.convertValueFromClient(frontpageconfig);
    return this.http.post<RestFrontpageconfig>(this.resourceUrl, copy).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(frontpageconfig: IFrontpageconfig): Observable<IFrontpageconfig> {
    const copy = this.convertValueFromClient(frontpageconfig);
    return this.http
      .put<RestFrontpageconfig>(`${this.resourceUrl}/${encodeURIComponent(this.getFrontpageconfigIdentifier(frontpageconfig))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(frontpageconfig: PartialUpdateFrontpageconfig): Observable<IFrontpageconfig> {
    const copy = this.convertValueFromClient(frontpageconfig);
    return this.http
      .patch<RestFrontpageconfig>(`${this.resourceUrl}/${encodeURIComponent(this.getFrontpageconfigIdentifier(frontpageconfig))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<IFrontpageconfig> {
    return this.http
      .get<RestFrontpageconfig>(`${this.resourceUrl}/${encodeURIComponent(id)}`)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IFrontpageconfig[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFrontpageconfig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => res.clone({ body: this.convertResponseArrayFromServer(res.body!) })));
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getFrontpageconfigIdentifier(frontpageconfig: Pick<IFrontpageconfig, 'id'>): number {
    return frontpageconfig.id;
  }

  compareFrontpageconfig(o1: Pick<IFrontpageconfig, 'id'> | null, o2: Pick<IFrontpageconfig, 'id'> | null): boolean {
    return o1 && o2 ? this.getFrontpageconfigIdentifier(o1) === this.getFrontpageconfigIdentifier(o2) : o1 === o2;
  }

  addFrontpageconfigToCollectionIfMissing<Type extends Pick<IFrontpageconfig, 'id'>>(
    frontpageconfigCollection: Type[],
    ...frontpageconfigsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const frontpageconfigs: Type[] = frontpageconfigsToCheck.filter(isPresent);
    if (frontpageconfigs.length > 0) {
      const frontpageconfigCollectionIdentifiers = frontpageconfigCollection.map(frontpageconfigItem =>
        this.getFrontpageconfigIdentifier(frontpageconfigItem),
      );
      const frontpageconfigsToAdd = frontpageconfigs.filter(frontpageconfigItem => {
        const frontpageconfigIdentifier = this.getFrontpageconfigIdentifier(frontpageconfigItem);
        if (frontpageconfigCollectionIdentifiers.includes(frontpageconfigIdentifier)) {
          return false;
        }
        frontpageconfigCollectionIdentifiers.push(frontpageconfigIdentifier);
        return true;
      });
      return [...frontpageconfigsToAdd, ...frontpageconfigCollection];
    }
    return frontpageconfigCollection;
  }

  protected convertValueFromClient<T extends IFrontpageconfig | NewFrontpageconfig | PartialUpdateFrontpageconfig>(
    frontpageconfig: T,
  ): RestOf<T> {
    return {
      ...frontpageconfig,
      creationDate: frontpageconfig.creationDate?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: RestFrontpageconfig): IFrontpageconfig {
    return this.convertValueFromServer(res);
  }

  protected convertResponseArrayFromServer(res: RestFrontpageconfig[]): IFrontpageconfig[] {
    return res.map(item => this.convertValueFromServer(item));
  }
}
