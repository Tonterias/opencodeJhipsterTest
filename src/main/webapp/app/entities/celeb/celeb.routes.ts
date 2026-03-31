import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CelebResolve from './route/celeb-routing-resolve.service';

const celebRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/celeb').then(m => m.Celeb),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/celeb-detail').then(m => m.CelebDetail),
    resolve: {
      celeb: CelebResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/celeb-update').then(m => m.CelebUpdate),
    resolve: {
      celeb: CelebResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/celeb-update').then(m => m.CelebUpdate),
    resolve: {
      celeb: CelebResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default celebRoute;
