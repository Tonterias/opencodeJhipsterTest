import { HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { IInterest } from '../interest.model';
import { InterestService } from '../service/interest.service';

const interestResolve = (route: ActivatedRouteSnapshot): Observable<null | IInterest> => {
  const id = route.params.id;
  if (id) {
    const router = inject(Router);
    const service = inject(InterestService);
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

export default interestResolve;
