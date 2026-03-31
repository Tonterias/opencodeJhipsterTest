import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IConfigVariables, NewConfigVariables } from '../config-variables.model';

export type PartialUpdateConfigVariables = Partial<IConfigVariables> & Pick<IConfigVariables, 'id'>;

@Injectable()
export class ConfigVariablesesService {
  readonly configVariablesesParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly configVariablesesResource = httpResource<IConfigVariables[]>(() => {
    const params = this.configVariablesesParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of configVariables that have been fetched. It is updated when the configVariablesesResource emits a new value.
   * In case of error while fetching the configVariableses, the signal is set to an empty array.
   */
  readonly configVariableses = computed(() => (this.configVariablesesResource.hasValue() ? this.configVariablesesResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/config-variables');
}

@Injectable({ providedIn: 'root' })
export class ConfigVariablesService extends ConfigVariablesesService {
  protected readonly http = inject(HttpClient);

  create(configVariables: NewConfigVariables): Observable<IConfigVariables> {
    return this.http.post<IConfigVariables>(this.resourceUrl, configVariables);
  }

  update(configVariables: IConfigVariables): Observable<IConfigVariables> {
    return this.http.put<IConfigVariables>(
      `${this.resourceUrl}/${encodeURIComponent(this.getConfigVariablesIdentifier(configVariables))}`,
      configVariables,
    );
  }

  partialUpdate(configVariables: PartialUpdateConfigVariables): Observable<IConfigVariables> {
    return this.http.patch<IConfigVariables>(
      `${this.resourceUrl}/${encodeURIComponent(this.getConfigVariablesIdentifier(configVariables))}`,
      configVariables,
    );
  }

  find(id: number): Observable<IConfigVariables> {
    return this.http.get<IConfigVariables>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<IConfigVariables[]>> {
    const options = createRequestOption(req);
    return this.http.get<IConfigVariables[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getConfigVariablesIdentifier(configVariables: Pick<IConfigVariables, 'id'>): number {
    return configVariables.id;
  }

  compareConfigVariables(o1: Pick<IConfigVariables, 'id'> | null, o2: Pick<IConfigVariables, 'id'> | null): boolean {
    return o1 && o2 ? this.getConfigVariablesIdentifier(o1) === this.getConfigVariablesIdentifier(o2) : o1 === o2;
  }

  addConfigVariablesToCollectionIfMissing<Type extends Pick<IConfigVariables, 'id'>>(
    configVariablesCollection: Type[],
    ...configVariablesesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const configVariableses: Type[] = configVariablesesToCheck.filter(isPresent);
    if (configVariableses.length > 0) {
      const configVariablesCollectionIdentifiers = configVariablesCollection.map(configVariablesItem =>
        this.getConfigVariablesIdentifier(configVariablesItem),
      );
      const configVariablesesToAdd = configVariableses.filter(configVariablesItem => {
        const configVariablesIdentifier = this.getConfigVariablesIdentifier(configVariablesItem);
        if (configVariablesCollectionIdentifiers.includes(configVariablesIdentifier)) {
          return false;
        }
        configVariablesCollectionIdentifiers.push(configVariablesIdentifier);
        return true;
      });
      return [...configVariablesesToAdd, ...configVariablesCollection];
    }
    return configVariablesCollection;
  }
}
