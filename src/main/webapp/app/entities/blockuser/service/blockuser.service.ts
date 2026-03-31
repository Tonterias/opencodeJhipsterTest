import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IBlockuser, NewBlockuser } from '../blockuser.model';

export type PartialUpdateBlockuser = Partial<IBlockuser> & Pick<IBlockuser, 'id'>;

type RestOf<T extends IBlockuser | NewBlockuser> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

export type RestBlockuser = RestOf<IBlockuser>;

export type NewRestBlockuser = RestOf<NewBlockuser>;

export type PartialUpdateRestBlockuser = RestOf<PartialUpdateBlockuser>;

@Injectable()
export class BlockusersService {
  readonly blockusersParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly blockusersResource = httpResource<RestBlockuser[]>(() => {
    const params = this.blockusersParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of blockuser that have been fetched. It is updated when the blockusersResource emits a new value.
   * In case of error while fetching the blockusers, the signal is set to an empty array.
   */
  readonly blockusers = computed(() =>
    (this.blockusersResource.hasValue() ? this.blockusersResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/blockusers');

  protected convertValueFromServer(restBlockuser: RestBlockuser): IBlockuser {
    return {
      ...restBlockuser,
      creationDate: restBlockuser.creationDate ? dayjs(restBlockuser.creationDate) : undefined,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class BlockuserService extends BlockusersService {
  protected readonly http = inject(HttpClient);

  create(blockuser: NewBlockuser): Observable<IBlockuser> {
    const copy = this.convertValueFromClient(blockuser);
    return this.http.post<RestBlockuser>(this.resourceUrl, copy).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(blockuser: IBlockuser): Observable<IBlockuser> {
    const copy = this.convertValueFromClient(blockuser);
    return this.http
      .put<RestBlockuser>(`${this.resourceUrl}/${encodeURIComponent(this.getBlockuserIdentifier(blockuser))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(blockuser: PartialUpdateBlockuser): Observable<IBlockuser> {
    const copy = this.convertValueFromClient(blockuser);
    return this.http
      .patch<RestBlockuser>(`${this.resourceUrl}/${encodeURIComponent(this.getBlockuserIdentifier(blockuser))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<IBlockuser> {
    return this.http
      .get<RestBlockuser>(`${this.resourceUrl}/${encodeURIComponent(id)}`)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IBlockuser[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<RestBlockuser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => res.clone({ body: this.convertResponseArrayFromServer(res.body!) })));
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getBlockuserIdentifier(blockuser: Pick<IBlockuser, 'id'>): number {
    return blockuser.id;
  }

  compareBlockuser(o1: Pick<IBlockuser, 'id'> | null, o2: Pick<IBlockuser, 'id'> | null): boolean {
    return o1 && o2 ? this.getBlockuserIdentifier(o1) === this.getBlockuserIdentifier(o2) : o1 === o2;
  }

  addBlockuserToCollectionIfMissing<Type extends Pick<IBlockuser, 'id'>>(
    blockuserCollection: Type[],
    ...blockusersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const blockusers: Type[] = blockusersToCheck.filter(isPresent);
    if (blockusers.length > 0) {
      const blockuserCollectionIdentifiers = blockuserCollection.map(blockuserItem => this.getBlockuserIdentifier(blockuserItem));
      const blockusersToAdd = blockusers.filter(blockuserItem => {
        const blockuserIdentifier = this.getBlockuserIdentifier(blockuserItem);
        if (blockuserCollectionIdentifiers.includes(blockuserIdentifier)) {
          return false;
        }
        blockuserCollectionIdentifiers.push(blockuserIdentifier);
        return true;
      });
      return [...blockusersToAdd, ...blockuserCollection];
    }
    return blockuserCollection;
  }

  protected convertValueFromClient<T extends IBlockuser | NewBlockuser | PartialUpdateBlockuser>(blockuser: T): RestOf<T> {
    return {
      ...blockuser,
      creationDate: blockuser.creationDate?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: RestBlockuser): IBlockuser {
    return this.convertValueFromServer(res);
  }

  protected convertResponseArrayFromServer(res: RestBlockuser[]): IBlockuser[] {
    return res.map(item => this.convertValueFromServer(item));
  }
}
