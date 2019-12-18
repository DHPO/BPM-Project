import Vue from 'vue';
import VueRouter from 'vue-router';
import Main from '../views/Main.vue';
import Login from '../views/Login.vue';
import Test from '../views/Test.vue';
import Controller from '../components/Controller/Controller.vue';
import Interactive from '../components/Interactive/Interactive.vue';
import ActivityEditView from '../views/ActivityEditView.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'main',
    component: Main,
    children: [
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
    path: '/send',
    name: 'send',
    component: Controller,
  },
  {
    path: '/receive',
    name: 'receive',
    component: Interactive,
  },
];

const router = new VueRouter({
  routes,
});

export default router;
