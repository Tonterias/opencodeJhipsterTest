import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IFollow, NewFollow } from '../follow.model';

export type PartialUpdateFollow = Partial<IFollow> & Pick<IFollow, 'id'>;

type RestOf<T extends IFollow | NewFollow> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

export type RestFollow = RestOf<IFollow>;

export type NewRestFollow = RestOf<NewFollow>;

export type PartialUpdateRestFollow = RestOf<PartialUpdateFollow>;

@Injectable()
export class FollowsService {
  readonly followsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly followsResource = httpResource<RestFollow[]>(() => {
    const params = this.followsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of follow that have been fetched. It is updated when the followsResource emits a new value.
   * In case of error while fetching the follows, the signal is set to an empty array.
   */
  readonly follows = computed(() =>
    (this.followsResource.hasValue() ? this.followsResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/follows');

  protected convertValueFromServer(restFollow: RestFollow): IFollow {
    return {
      ...restFollow,
      creationDate: restFollow.creationDate ? dayjs(restFollow.creationDate) : undefined,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class FollowService extends FollowsService {
  protected readonly http = inject(HttpClient);

  create(follow: NewFollow): Observable<IFollow> {
    const copy = this.convertValueFromClient(follow);
    return this.http.post<RestFollow>(this.resourceUrl, copy).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(follow: IFollow): Observable<IFollow> {
    const copy = this.convertValueFromClient(follow);
    return this.http
      .put<RestFollow>(`${this.resourceUrl}/${encodeURIComponent(this.getFollowIdentifier(follow))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(follow: PartialUpdateFollow): Observable<IFollow> {
    const copy = this.convertValueFromClient(follow);
    return this.http
      .patch<RestFollow>(`${this.resourceUrl}/${encodeURIComponent(this.getFollowIdentifier(follow))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<IFollow> {
    return this.http.get<RestFollow>(`${this.resourceUrl}/${encodeURIComponent(id)}`).pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IFollow[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFollow[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => res.clone({ body: this.convertResponseArrayFromServer(res.body!) })));
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getFollowIdentifier(follow: Pick<IFollow, 'id'>): number {
    return follow.id;
  }

  compareFollow(o1: Pick<IFollow, 'id'> | null, o2: Pick<IFollow, 'id'> | null): boolean {
    return o1 && o2 ? this.getFollowIdentifier(o1) === this.getFollowIdentifier(o2) : o1 === o2;
  }

  addFollowToCollectionIfMissing<Type extends Pick<IFollow, 'id'>>(
    followCollection: Type[],
    ...followsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const follows: Type[] = followsToCheck.filter(isPresent);
    if (follows.length > 0) {
      const followCollectionIdentifiers = followCollection.map(followItem => this.getFollowIdentifier(followItem));
      const followsToAdd = follows.filter(followItem => {
        const followIdentifier = this.getFollowIdentifier(followItem);
        if (followCollectionIdentifiers.includes(followIdentifier)) {
          return false;
        }
        followCollectionIdentifiers.push(followIdentifier);
        return true;
      });
      return [...followsToAdd, ...followCollection];
    }
    return followCollection;
  }

  protected convertValueFromClient<T extends IFollow | NewFollow | PartialUpdateFollow>(follow: T): RestOf<T> {
    return {
      ...follow,
      creationDate: follow.creationDate?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: RestFollow): IFollow {
    return this.convertValueFromServer(res);
  }

  protected convertResponseArrayFromServer(res: RestFollow[]): IFollow[] {
    return res.map(item => this.convertValueFromServer(item));
  }
}
