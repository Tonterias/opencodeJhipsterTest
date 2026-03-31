import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CcelebResolve from './route/cceleb-routing-resolve.service';

const ccelebRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cceleb').then(m => m.Cceleb),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/cceleb-detail').then(m => m.CcelebDetail),
    resolve: {
      cceleb: CcelebResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cceleb-update').then(m => m.CcelebUpdate),
    resolve: {
      cceleb: CcelebResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/cceleb-update').then(m => m.CcelebUpdate),
    resolve: {
      cceleb: CcelebResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default ccelebRoute;
