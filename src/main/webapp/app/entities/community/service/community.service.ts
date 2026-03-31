import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICommunity, NewCommunity } from '../community.model';

export type PartialUpdateCommunity = Partial<ICommunity> & Pick<ICommunity, 'id'>;

type RestOf<T extends ICommunity | NewCommunity> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

export type RestCommunity = RestOf<ICommunity>;

export type NewRestCommunity = RestOf<NewCommunity>;

export type PartialUpdateRestCommunity = RestOf<PartialUpdateCommunity>;

@Injectable()
export class CommunitiesService {
  readonly communitiesParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly communitiesResource = httpResource<RestCommunity[]>(() => {
    const params = this.communitiesParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of community that have been fetched. It is updated when the communitiesResource emits a new value.
   * In case of error while fetching the communities, the signal is set to an empty array.
   */
  readonly communities = computed(() =>
    (this.communitiesResource.hasValue() ? this.communitiesResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/communities');

  protected convertValueFromServer(restCommunity: RestCommunity): ICommunity {
    return {
      ...restCommunity,
      creationDate: restCommunity.creationDate ? dayjs(restCommunity.creationDate) : undefined,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class CommunityService extends CommunitiesService {
  protected readonly http = inject(HttpClient);

  create(community: NewCommunity): Observable<ICommunity> {
    const copy = this.convertValueFromClient(community);
    return this.http.post<RestCommunity>(this.resourceUrl, copy).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(community: ICommunity): Observable<ICommunity> {
    const copy = this.convertValueFromClient(community);
    return this.http
      .put<RestCommunity>(`${this.resourceUrl}/${encodeURIComponent(this.getCommunityIdentifier(community))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(community: PartialUpdateCommunity): Observable<ICommunity> {
    const copy = this.convertValueFromClient(community);
    return this.http
      .patch<RestCommunity>(`${this.resourceUrl}/${encodeURIComponent(this.getCommunityIdentifier(community))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<ICommunity> {
    return this.http
      .get<RestCommunity>(`${this.resourceUrl}/${encodeURIComponent(id)}`)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<ICommunity[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCommunity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => res.clone({ body: this.convertResponseArrayFromServer(res.body!) })));
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getCommunityIdentifier(community: Pick<ICommunity, 'id'>): number {
    return community.id;
  }

  compareCommunity(o1: Pick<ICommunity, 'id'> | null, o2: Pick<ICommunity, 'id'> | null): boolean {
    return o1 && o2 ? this.getCommunityIdentifier(o1) === this.getCommunityIdentifier(o2) : o1 === o2;
  }

  addCommunityToCollectionIfMissing<Type extends Pick<ICommunity, 'id'>>(
    communityCollection: Type[],
    ...communitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const communities: Type[] = communitiesToCheck.filter(isPresent);
    if (communities.length > 0) {
      const communityCollectionIdentifiers = communityCollection.map(communityItem => this.getCommunityIdentifier(communityItem));
      const communitiesToAdd = communities.filter(communityItem => {
        const communityIdentifier = this.getCommunityIdentifier(communityItem);
        if (communityCollectionIdentifiers.includes(communityIdentifier)) {
          return false;
        }
        communityCollectionIdentifiers.push(communityIdentifier);
        return true;
      });
      return [...communitiesToAdd, ...communityCollection];
    }
    return communityCollection;
  }

  protected convertValueFromClient<T extends ICommunity | NewCommunity | PartialUpdateCommunity>(community: T): RestOf<T> {
    return {
      ...community,
      creationDate: community.creationDate?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: RestCommunity): ICommunity {
    return this.convertValueFromServer(res);
  }

  protected convertResponseArrayFromServer(res: RestCommunity[]): ICommunity[] {
    return res.map(item => this.convertValueFromServer(item));
  }
}
