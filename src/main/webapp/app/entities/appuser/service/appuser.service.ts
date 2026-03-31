import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IAppuser, NewAppuser } from '../appuser.model';

export type PartialUpdateAppuser = Partial<IAppuser> & Pick<IAppuser, 'id'>;

type RestOf<T extends IAppuser | NewAppuser> = Omit<T, 'creationDate' | 'birthdate'> & {
  creationDate?: string | null;
  birthdate?: string | null;
};

export type RestAppuser = RestOf<IAppuser>;

export type NewRestAppuser = RestOf<NewAppuser>;

export type PartialUpdateRestAppuser = RestOf<PartialUpdateAppuser>;

@Injectable()
export class AppusersService {
  readonly appusersParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly appusersResource = httpResource<RestAppuser[]>(() => {
    const params = this.appusersParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of appuser that have been fetched. It is updated when the appusersResource emits a new value.
   * In case of error while fetching the appusers, the signal is set to an empty array.
   */
  readonly appusers = computed(() =>
    (this.appusersResource.hasValue() ? this.appusersResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/appusers');

  protected convertValueFromServer(restAppuser: RestAppuser): IAppuser {
    return {
      ...restAppuser,
      creationDate: restAppuser.creationDate ? dayjs(restAppuser.creationDate) : undefined,
      birthdate: restAppuser.birthdate ? dayjs(restAppuser.birthdate) : undefined,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class AppuserService extends AppusersService {
  protected readonly http = inject(HttpClient);

  create(appuser: NewAppuser): Observable<IAppuser> {
    const copy = this.convertValueFromClient(appuser);
    return this.http.post<RestAppuser>(this.resourceUrl, copy).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(appuser: IAppuser): Observable<IAppuser> {
    const copy = this.convertValueFromClient(appuser);
    return this.http
      .put<RestAppuser>(`${this.resourceUrl}/${encodeURIComponent(this.getAppuserIdentifier(appuser))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(appuser: PartialUpdateAppuser): Observable<IAppuser> {
    const copy = this.convertValueFromClient(appuser);
    return this.http
      .patch<RestAppuser>(`${this.resourceUrl}/${encodeURIComponent(this.getAppuserIdentifier(appuser))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<IAppuser> {
    return this.http
      .get<RestAppuser>(`${this.resourceUrl}/${encodeURIComponent(id)}`)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IAppuser[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAppuser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => res.clone({ body: this.convertResponseArrayFromServer(res.body!) })));
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getAppuserIdentifier(appuser: Pick<IAppuser, 'id'>): number {
    return appuser.id;
  }

  compareAppuser(o1: Pick<IAppuser, 'id'> | null, o2: Pick<IAppuser, 'id'> | null): boolean {
    return o1 && o2 ? this.getAppuserIdentifier(o1) === this.getAppuserIdentifier(o2) : o1 === o2;
  }

  addAppuserToCollectionIfMissing<Type extends Pick<IAppuser, 'id'>>(
    appuserCollection: Type[],
    ...appusersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const appusers: Type[] = appusersToCheck.filter(isPresent);
    if (appusers.length > 0) {
      const appuserCollectionIdentifiers = appuserCollection.map(appuserItem => this.getAppuserIdentifier(appuserItem));
      const appusersToAdd = appusers.filter(appuserItem => {
        const appuserIdentifier = this.getAppuserIdentifier(appuserItem);
        if (appuserCollectionIdentifiers.includes(appuserIdentifier)) {
          return false;
        }
        appuserCollectionIdentifiers.push(appuserIdentifier);
        return true;
      });
      return [...appusersToAdd, ...appuserCollection];
    }
    return appuserCollection;
  }

  protected convertValueFromClient<T extends IAppuser | NewAppuser | PartialUpdateAppuser>(appuser: T): RestOf<T> {
    return {
      ...appuser,
      creationDate: appuser.creationDate?.toJSON() ?? null,
      birthdate: appuser.birthdate?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: RestAppuser): IAppuser {
    return this.convertValueFromServer(res);
  }

  protected convertResponseArrayFromServer(res: RestAppuser[]): IAppuser[] {
    return res.map(item => this.convertValueFromServer(item));
  }
}
