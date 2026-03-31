import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CommunityResolve from './route/community-routing-resolve.service';

const communityRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/community').then(m => m.Community),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/community-detail').then(m => m.CommunityDetail),
    resolve: {
      community: CommunityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/community-update').then(m => m.CommunityUpdate),
    resolve: {
      community: CommunityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/community-update').then(m => m.CommunityUpdate),
    resolve: {
      community: CommunityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default communityRoute;
