import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'opencodetestApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'user-management',
    data: { pageTitle: 'userManagement.home.title' },
    loadChildren: () => import('./admin/user-management/user-management.routes'),
  },
  {
    path: 'appuser',
    data: { pageTitle: 'opencodetestApp.appuser.home.title' },
    loadChildren: () => import('./appuser/appuser.routes'),
  },
  {
    path: 'blog',
    data: { pageTitle: 'opencodetestApp.blog.home.title' },
    loadChildren: () => import('./blog/blog.routes'),
  },
  {
    path: 'post',
    data: { pageTitle: 'opencodetestApp.post.home.title' },
    loadChildren: () => import('./post/post.routes'),
  },
  {
    path: 'topic',
    data: { pageTitle: 'opencodetestApp.topic.home.title' },
    loadChildren: () => import('./topic/topic.routes'),
  },
  {
    path: 'tag',
    data: { pageTitle: 'opencodetestApp.tag.home.title' },
    loadChildren: () => import('./tag/tag.routes'),
  },
  {
    path: 'comment',
    data: { pageTitle: 'opencodetestApp.comment.home.title' },
    loadChildren: () => import('./comment/comment.routes'),
  },
  {
    path: 'notification',
    data: { pageTitle: 'opencodetestApp.notification.home.title' },
    loadChildren: () => import('./notification/notification.routes'),
  },
  {
    path: 'appphoto',
    data: { pageTitle: 'opencodetestApp.appphoto.home.title' },
    loadChildren: () => import('./appphoto/appphoto.routes'),
  },
  {
    path: 'community',
    data: { pageTitle: 'opencodetestApp.community.home.title' },
    loadChildren: () => import('./community/community.routes'),
  },
  {
    path: 'follow',
    data: { pageTitle: 'opencodetestApp.follow.home.title' },
    loadChildren: () => import('./follow/follow.routes'),
  },
  {
    path: 'blockuser',
    data: { pageTitle: 'opencodetestApp.blockuser.home.title' },
    loadChildren: () => import('./blockuser/blockuser.routes'),
  },
  {
    path: 'interest',
    data: { pageTitle: 'opencodetestApp.interest.home.title' },
    loadChildren: () => import('./interest/interest.routes'),
  },
  {
    path: 'activity',
    data: { pageTitle: 'opencodetestApp.activity.home.title' },
    loadChildren: () => import('./activity/activity.routes'),
  },
  {
    path: 'celeb',
    data: { pageTitle: 'opencodetestApp.celeb.home.title' },
    loadChildren: () => import('./celeb/celeb.routes'),
  },
  {
    path: 'cinterest',
    data: { pageTitle: 'opencodetestApp.cinterest.home.title' },
    loadChildren: () => import('./cinterest/cinterest.routes'),
  },
  {
    path: 'cactivity',
    data: { pageTitle: 'opencodetestApp.cactivity.home.title' },
    loadChildren: () => import('./cactivity/cactivity.routes'),
  },
  {
    path: 'cceleb',
    data: { pageTitle: 'opencodetestApp.cceleb.home.title' },
    loadChildren: () => import('./cceleb/cceleb.routes'),
  },
  {
    path: 'urllink',
    data: { pageTitle: 'opencodetestApp.urllink.home.title' },
    loadChildren: () => import('./urllink/urllink.routes'),
  },
  {
    path: 'frontpageconfig',
    data: { pageTitle: 'opencodetestApp.frontpageconfig.home.title' },
    loadChildren: () => import('./frontpageconfig/frontpageconfig.routes'),
  },
  {
    path: 'feedback',
    data: { pageTitle: 'opencodetestApp.feedback.home.title' },
    loadChildren: () => import('./feedback/feedback.routes'),
  },
  {
    path: 'config-variables',
    data: { pageTitle: 'opencodetestApp.configVariables.home.title' },
    loadChildren: () => import('./config-variables/config-variables.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
