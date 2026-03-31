import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import AppuserResolve from './route/appuser-routing-resolve.service';

const appuserRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/appuser').then(m => m.Appuser),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/appuser-detail').then(m => m.AppuserDetail),
    resolve: {
      appuser: AppuserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/appuser-update').then(m => m.AppuserUpdate),
    resolve: {
      appuser: AppuserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/appuser-update').then(m => m.AppuserUpdate),
    resolve: {
      appuser: AppuserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default appuserRoute;
