import Vue from 'vue';
import VueRouter from 'vue-router';
import Main from '../views/Main.vue';
import Login from '../views/Login.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'main',
    component: Main,
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
];

const router = new VueRouter({
  routes,
});

export default router;
