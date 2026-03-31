import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IFeedback, NewFeedback } from '../feedback.model';

export type PartialUpdateFeedback = Partial<IFeedback> & Pick<IFeedback, 'id'>;

type RestOf<T extends IFeedback | NewFeedback> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

export type RestFeedback = RestOf<IFeedback>;

export type NewRestFeedback = RestOf<NewFeedback>;

export type PartialUpdateRestFeedback = RestOf<PartialUpdateFeedback>;

@Injectable()
export class FeedbacksService {
  readonly feedbacksParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly feedbacksResource = httpResource<RestFeedback[]>(() => {
    const params = this.feedbacksParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of feedback that have been fetched. It is updated when the feedbacksResource emits a new value.
   * In case of error while fetching the feedbacks, the signal is set to an empty array.
   */
  readonly feedbacks = computed(() =>
    (this.feedbacksResource.hasValue() ? this.feedbacksResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/feedbacks');

  protected convertValueFromServer(restFeedback: RestFeedback): IFeedback {
    return {
      ...restFeedback,
      creationDate: restFeedback.creationDate ? dayjs(restFeedback.creationDate) : undefined,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class FeedbackService extends FeedbacksService {
  protected readonly http = inject(HttpClient);

  create(feedback: NewFeedback): Observable<IFeedback> {
    const copy = this.convertValueFromClient(feedback);
    return this.http.post<RestFeedback>(this.resourceUrl, copy).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(feedback: IFeedback): Observable<IFeedback> {
    const copy = this.convertValueFromClient(feedback);
    return this.http
      .put<RestFeedback>(`${this.resourceUrl}/${encodeURIComponent(this.getFeedbackIdentifier(feedback))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(feedback: PartialUpdateFeedback): Observable<IFeedback> {
    const copy = this.convertValueFromClient(feedback);
    return this.http
      .patch<RestFeedback>(`${this.resourceUrl}/${encodeURIComponent(this.getFeedbackIdentifier(feedback))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<IFeedback> {
    return this.http
      .get<RestFeedback>(`${this.resourceUrl}/${encodeURIComponent(id)}`)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IFeedback[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFeedback[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => res.clone({ body: this.convertResponseArrayFromServer(res.body!) })));
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getFeedbackIdentifier(feedback: Pick<IFeedback, 'id'>): number {
    return feedback.id;
  }

  compareFeedback(o1: Pick<IFeedback, 'id'> | null, o2: Pick<IFeedback, 'id'> | null): boolean {
    return o1 && o2 ? this.getFeedbackIdentifier(o1) === this.getFeedbackIdentifier(o2) : o1 === o2;
  }

  addFeedbackToCollectionIfMissing<Type extends Pick<IFeedback, 'id'>>(
    feedbackCollection: Type[],
    ...feedbacksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const feedbacks: Type[] = feedbacksToCheck.filter(isPresent);
    if (feedbacks.length > 0) {
      const feedbackCollectionIdentifiers = feedbackCollection.map(feedbackItem => this.getFeedbackIdentifier(feedbackItem));
      const feedbacksToAdd = feedbacks.filter(feedbackItem => {
        const feedbackIdentifier = this.getFeedbackIdentifier(feedbackItem);
        if (feedbackCollectionIdentifiers.includes(feedbackIdentifier)) {
          return false;
        }
        feedbackCollectionIdentifiers.push(feedbackIdentifier);
        return true;
      });
      return [...feedbacksToAdd, ...feedbackCollection];
    }
    return feedbackCollection;
  }

  protected convertValueFromClient<T extends IFeedback | NewFeedback | PartialUpdateFeedback>(feedback: T): RestOf<T> {
    return {
      ...feedback,
      creationDate: feedback.creationDate?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: RestFeedback): IFeedback {
    return this.convertValueFromServer(res);
  }

  protected convertResponseArrayFromServer(res: RestFeedback[]): IFeedback[] {
    return res.map(item => this.convertValueFromServer(item));
  }
}
