import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import UrllinkResolve from './route/urllink-routing-resolve.service';

const urllinkRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/urllink').then(m => m.Urllink),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/urllink-detail').then(m => m.UrllinkDetail),
    resolve: {
      urllink: UrllinkResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/urllink-update').then(m => m.UrllinkUpdate),
    resolve: {
      urllink: UrllinkResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/urllink-update').then(m => m.UrllinkUpdate),
    resolve: {
      urllink: UrllinkResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default urllinkRoute;
