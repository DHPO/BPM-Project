import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false;

Vue.use(ElementUI);
// @ts-ignore
import barrage from 'vue2-barrage';
Vue.use(barrage);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
