import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import AppphotoResolve from './route/appphoto-routing-resolve.service';

const appphotoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/appphoto').then(m => m.Appphoto),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/appphoto-detail').then(m => m.AppphotoDetail),
    resolve: {
      appphoto: AppphotoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/appphoto-update').then(m => m.AppphotoUpdate),
    resolve: {
      appphoto: AppphotoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/appphoto-update').then(m => m.AppphotoUpdate),
    resolve: {
      appphoto: AppphotoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default appphotoRoute;
