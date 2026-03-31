import { HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { ICommunity } from '../community.model';
import { CommunityService } from '../service/community.service';

const communityResolve = (route: ActivatedRouteSnapshot): Observable<null | ICommunity> => {
  const id = route.params.id;
  if (id) {
    const router = inject(Router);
    const service = inject(CommunityService);
    return service.find(id).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 404) {
          router.navigate(['404']);
        } else {
          router.navigate(['error']);
        }
        return EMPTY;
      }),
    );
  }

  return of(null);
};

export default communityResolve;
