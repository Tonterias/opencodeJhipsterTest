import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import FollowResolve from './route/follow-routing-resolve.service';

const followRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/follow').then(m => m.Follow),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/follow-detail').then(m => m.FollowDetail),
    resolve: {
      follow: FollowResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/follow-update').then(m => m.FollowUpdate),
    resolve: {
      follow: FollowResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/follow-update').then(m => m.FollowUpdate),
    resolve: {
      follow: FollowResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default followRoute;
