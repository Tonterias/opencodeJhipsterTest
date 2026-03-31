import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CinterestResolve from './route/cinterest-routing-resolve.service';

const cinterestRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cinterest').then(m => m.Cinterest),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/cinterest-detail').then(m => m.CinterestDetail),
    resolve: {
      cinterest: CinterestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cinterest-update').then(m => m.CinterestUpdate),
    resolve: {
      cinterest: CinterestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/cinterest-update').then(m => m.CinterestUpdate),
    resolve: {
      cinterest: CinterestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cinterestRoute;
