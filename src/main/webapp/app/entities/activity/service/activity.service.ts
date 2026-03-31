import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IActivity, NewActivity } from '../activity.model';

export type PartialUpdateActivity = Partial<IActivity> & Pick<IActivity, 'id'>;

@Injectable()
export class ActivitiesService {
  readonly activitiesParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly activitiesResource = httpResource<IActivity[]>(() => {
    const params = this.activitiesParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of activity that have been fetched. It is updated when the activitiesResource emits a new value.
   * In case of error while fetching the activities, the signal is set to an empty array.
   */
  readonly activities = computed(() => (this.activitiesResource.hasValue() ? this.activitiesResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/activities');
}

@Injectable({ providedIn: 'root' })
export class ActivityService extends ActivitiesService {
  protected readonly http = inject(HttpClient);

  create(activity: NewActivity): Observable<IActivity> {
    return this.http.post<IActivity>(this.resourceUrl, activity);
  }

  update(activity: IActivity): Observable<IActivity> {
    return this.http.put<IActivity>(`${this.resourceUrl}/${encodeURIComponent(this.getActivityIdentifier(activity))}`, activity);
  }

  partialUpdate(activity: PartialUpdateActivity): Observable<IActivity> {
    return this.http.patch<IActivity>(`${this.resourceUrl}/${encodeURIComponent(this.getActivityIdentifier(activity))}`, activity);
  }

  find(id: number): Observable<IActivity> {
    return this.http.get<IActivity>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<IActivity[]>> {
    const options = createRequestOption(req);
    return this.http.get<IActivity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getActivityIdentifier(activity: Pick<IActivity, 'id'>): number {
    return activity.id;
  }

  compareActivity(o1: Pick<IActivity, 'id'> | null, o2: Pick<IActivity, 'id'> | null): boolean {
    return o1 && o2 ? this.getActivityIdentifier(o1) === this.getActivityIdentifier(o2) : o1 === o2;
  }

  addActivityToCollectionIfMissing<Type extends Pick<IActivity, 'id'>>(
    activityCollection: Type[],
    ...activitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const activities: Type[] = activitiesToCheck.filter(isPresent);
    if (activities.length > 0) {
      const activityCollectionIdentifiers = activityCollection.map(activityItem => this.getActivityIdentifier(activityItem));
      const activitiesToAdd = activities.filter(activityItem => {
        const activityIdentifier = this.getActivityIdentifier(activityItem);
        if (activityCollectionIdentifiers.includes(activityIdentifier)) {
          return false;
        }
        activityCollectionIdentifiers.push(activityIdentifier);
        return true;
      });
      return [...activitiesToAdd, ...activityCollection];
    }
    return activityCollection;
  }
}
