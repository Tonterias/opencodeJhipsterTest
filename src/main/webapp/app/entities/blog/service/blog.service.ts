import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IBlog, NewBlog } from '../blog.model';

export type PartialUpdateBlog = Partial<IBlog> & Pick<IBlog, 'id'>;

type RestOf<T extends IBlog | NewBlog> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

export type RestBlog = RestOf<IBlog>;

export type NewRestBlog = RestOf<NewBlog>;

export type PartialUpdateRestBlog = RestOf<PartialUpdateBlog>;

@Injectable()
export class BlogsService {
  readonly blogsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(undefined);
  readonly blogsResource = httpResource<RestBlog[]>(() => {
    const params = this.blogsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of blog that have been fetched. It is updated when the blogsResource emits a new value.
   * In case of error while fetching the blogs, the signal is set to an empty array.
   */
  readonly blogs = computed(() =>
    (this.blogsResource.hasValue() ? this.blogsResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/blogs');

  protected convertValueFromServer(restBlog: RestBlog): IBlog {
    return {
      ...restBlog,
      creationDate: restBlog.creationDate ? dayjs(restBlog.creationDate) : undefined,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class BlogService extends BlogsService {
  protected readonly http = inject(HttpClient);

  create(blog: NewBlog): Observable<IBlog> {
    const copy = this.convertValueFromClient(blog);
    return this.http.post<RestBlog>(this.resourceUrl, copy).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(blog: IBlog): Observable<IBlog> {
    const copy = this.convertValueFromClient(blog);
    return this.http
      .put<RestBlog>(`${this.resourceUrl}/${encodeURIComponent(this.getBlogIdentifier(blog))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(blog: PartialUpdateBlog): Observable<IBlog> {
    const copy = this.convertValueFromClient(blog);
    return this.http
      .patch<RestBlog>(`${this.resourceUrl}/${encodeURIComponent(this.getBlogIdentifier(blog))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<IBlog> {
    return this.http.get<RestBlog>(`${this.resourceUrl}/${encodeURIComponent(id)}`).pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IBlog[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<RestBlog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => res.clone({ body: this.convertResponseArrayFromServer(res.body!) })));
  }

  delete(id: number): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getBlogIdentifier(blog: Pick<IBlog, 'id'>): number {
    return blog.id;
  }

  compareBlog(o1: Pick<IBlog, 'id'> | null, o2: Pick<IBlog, 'id'> | null): boolean {
    return o1 && o2 ? this.getBlogIdentifier(o1) === this.getBlogIdentifier(o2) : o1 === o2;
  }

  addBlogToCollectionIfMissing<Type extends Pick<IBlog, 'id'>>(
    blogCollection: Type[],
    ...blogsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const blogs: Type[] = blogsToCheck.filter(isPresent);
    if (blogs.length > 0) {
      const blogCollectionIdentifiers = blogCollection.map(blogItem => this.getBlogIdentifier(blogItem));
      const blogsToAdd = blogs.filter(blogItem => {
        const blogIdentifier = this.getBlogIdentifier(blogItem);
        if (blogCollectionIdentifiers.includes(blogIdentifier)) {
          return false;
        }
        blogCollectionIdentifiers.push(blogIdentifier);
        return true;
      });
      return [...blogsToAdd, ...blogCollection];
    }
    return blogCollection;
  }

  protected convertValueFromClient<T extends IBlog | NewBlog | PartialUpdateBlog>(blog: T): RestOf<T> {
    return {
      ...blog,
      creationDate: blog.creationDate?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: RestBlog): IBlog {
    return this.convertValueFromServer(res);
  }

  protected convertResponseArrayFromServer(res: RestBlog[]): IBlog[] {
    return res.map(item => this.convertValueFromServer(item));
  }
}
