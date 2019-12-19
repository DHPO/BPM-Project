import Vue from 'vue';
import VueRouter from 'vue-router';
import Main from '../views/Main.vue';
import Login from '../views/Login.vue';
import Test from '../views/Test.vue';
import Controller from '../components/Controller/Controller.vue';
import Interactive from '../components/Interactive/Interactive.vue';
import ActivityEditView from '../views/ActivityEditView.vue';
import ActivityView from '../views/ActivityView.vue';
import AdminManageView from '../views/AdminManage.vue';
import OrganizerManageView from '../views/OrganizerManage.vue';
import ControllerView from '../views/Controller.vue';
import ScreenView from '../views/Screen.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'main',
    component: Main,
    children: [
      {
        path: 'admin',
        component: AdminManageView,
      },
      {
        path: 'manage',
        component: OrganizerManageView,
      },
      {
        path: 'activity/edit/:id',
        component: ActivityEditView,
        props: true,
      },
      {
        path: 'activity/edit',
        component: ActivityEditView,
        props: {
          id: null,
        },
      },
      {
        path: 'activity/:id',
        component: ActivityView,
        props: true,
      },
    ],
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
  {
    path: '/test',
    name: 'test',
    component: Test,
  },
  {
    path: '/controller/:id',
    name: 'controller',
    props: true,
    component: ControllerView,
  },
  {
    path: '/screen',
    name: 'screen',
    component: ScreenView,
  },
];

const router = new VueRouter({
  routes,
});

export default router;
