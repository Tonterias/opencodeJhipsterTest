import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import BlockuserResolve from './route/blockuser-routing-resolve.service';

const blockuserRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/blockuser').then(m => m.Blockuser),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/blockuser-detail').then(m => m.BlockuserDetail),
    resolve: {
      blockuser: BlockuserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/blockuser-update').then(m => m.BlockuserUpdate),
    resolve: {
      blockuser: BlockuserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/blockuser-update').then(m => m.BlockuserUpdate),
    resolve: {
      blockuser: BlockuserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default blockuserRoute;
