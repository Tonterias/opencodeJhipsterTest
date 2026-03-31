import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CactivityResolve from './route/cactivity-routing-resolve.service';

const cactivityRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cactivity').then(m => m.Cactivity),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/cactivity-detail').then(m => m.CactivityDetail),
    resolve: {
      cactivity: CactivityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cactivity-update').then(m => m.CactivityUpdate),
    resolve: {
      cactivity: CactivityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/cactivity-update').then(m => m.CactivityUpdate),
    resolve: {
      cactivity: CactivityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cactivityRoute;
