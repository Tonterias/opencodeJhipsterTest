import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import FrontpageconfigResolve from './route/frontpageconfig-routing-resolve.service';

const frontpageconfigRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/frontpageconfig').then(m => m.Frontpageconfig),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/frontpageconfig-detail').then(m => m.FrontpageconfigDetail),
    resolve: {
      frontpageconfig: FrontpageconfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/frontpageconfig-update').then(m => m.FrontpageconfigUpdate),
    resolve: {
      frontpageconfig: FrontpageconfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/frontpageconfig-update').then(m => m.FrontpageconfigUpdate),
    resolve: {
      frontpageconfig: FrontpageconfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default frontpageconfigRoute;
