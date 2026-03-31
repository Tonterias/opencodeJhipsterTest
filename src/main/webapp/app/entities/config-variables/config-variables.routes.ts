import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import ConfigVariablesResolve from './route/config-variables-routing-resolve.service';

const configVariablesRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/config-variables').then(m => m.ConfigVariables),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/config-variables-detail').then(m => m.ConfigVariablesDetail),
    resolve: {
      configVariables: ConfigVariablesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/config-variables-update').then(m => m.ConfigVariablesUpdate),
    resolve: {
      configVariables: ConfigVariablesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/config-variables-update').then(m => m.ConfigVariablesUpdate),
    resolve: {
      configVariables: ConfigVariablesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default configVariablesRoute;
