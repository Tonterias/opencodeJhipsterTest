import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IAppphoto, NewAppphoto } from '../appphoto.model';

export type PartialUpdateAppphoto = Partial<IAppphoto> & Pick<IAppphoto, 'id'>;

type RestOf<T extends IAppphoto | NewAppphoto> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

export type RestAppphoto = RestOf<IAppphoto>;

export type NewRestAppphoto = RestOf<NewAppphoto>;

export type PartialUpdateRestAppphoto = RestOf<PartialUpdateAppphoto>;

@Injectable()
export class AppphotosService {
  readonly appphotosParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly appphotosResource = httpResource<RestAppphoto[]>(() => {
    const params = this.appphotosParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of appphoto that have been fetched. It is updated when the appphotosResource emits a new value.
   * In case of error while fetching the appphotos, the signal is set to an empty array.
   */
  readonly appphotos = computed(() =>
    (this.appphotosResource.hasValue() ? this.appphotosResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/appphotos');

  protected convertValueFromServer(restAppphoto: RestAppphoto): IAppphoto {
    return {
      ...restAppphoto,
      creationDate: restAppphoto.creationDate ? dayjs(restAppphoto.creationDate) : undefined,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class AppphotoService extends AppphotosService {
  protected readonly http = inject(HttpClient);

  create(appphoto: NewAppphoto): Observable<IAppphoto> {
    const copy = this.convertValueFromClient(appphoto);
    return this.http.post<RestAppphoto>(this.resourceUrl, copy).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(appphoto: IAppphoto): Observable<IAppphoto> {
    const copy = this.convertValueFromClient(appphoto);
    return this.http
      .put<RestAppphoto>(`${this.resourceUrl}/${encodeURIComponent(this.getAppphotoIdentifier(appphoto))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(appphoto: PartialUpdateAppphoto): Observable<IAppphoto> {
    const copy = this.convertValueFromClient(appphoto);
    return this.http
      .patch<RestAppphoto>(`${this.resourceUrl}/${encodeURIComponent(this.getAppphotoIdentifier(appphoto))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<IAppphoto> {
    return this.http
      .get<RestAppphoto>(`${this.resourceUrl}/${encodeURIComponent(id)}`)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IAppphoto[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAppphoto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => res.clone({ body: this.convertResponseArrayFromServer(res.body!) })));
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getAppphotoIdentifier(appphoto: Pick<IAppphoto, 'id'>): number {
    return appphoto.id;
  }

  compareAppphoto(o1: Pick<IAppphoto, 'id'> | null, o2: Pick<IAppphoto, 'id'> | null): boolean {
    return o1 && o2 ? this.getAppphotoIdentifier(o1) === this.getAppphotoIdentifier(o2) : o1 === o2;
  }

  addAppphotoToCollectionIfMissing<Type extends Pick<IAppphoto, 'id'>>(
    appphotoCollection: Type[],
    ...appphotosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const appphotos: Type[] = appphotosToCheck.filter(isPresent);
    if (appphotos.length > 0) {
      const appphotoCollectionIdentifiers = appphotoCollection.map(appphotoItem => this.getAppphotoIdentifier(appphotoItem));
      const appphotosToAdd = appphotos.filter(appphotoItem => {
        const appphotoIdentifier = this.getAppphotoIdentifier(appphotoItem);
        if (appphotoCollectionIdentifiers.includes(appphotoIdentifier)) {
          return false;
        }
        appphotoCollectionIdentifiers.push(appphotoIdentifier);
        return true;
      });
      return [...appphotosToAdd, ...appphotoCollection];
    }
    return appphotoCollection;
  }

  protected convertValueFromClient<T extends IAppphoto | NewAppphoto | PartialUpdateAppphoto>(appphoto: T): RestOf<T> {
    return {
      ...appphoto,
      creationDate: appphoto.creationDate?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: RestAppphoto): IAppphoto {
    return this.convertValueFromServer(res);
  }

  protected convertResponseArrayFromServer(res: RestAppphoto[]): IAppphoto[] {
    return res.map(item => this.convertValueFromServer(item));
  }
}
