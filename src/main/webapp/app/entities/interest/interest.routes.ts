import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import InterestResolve from './route/interest-routing-resolve.service';

const interestRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/interest').then(m => m.Interest),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/interest-detail').then(m => m.InterestDetail),
    resolve: {
      interest: InterestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/interest-update').then(m => m.InterestUpdate),
    resolve: {
      interest: InterestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/interest-update').then(m => m.InterestUpdate),
    resolve: {
      interest: InterestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default interestRoute;
