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

// @ts-ignore
import VueAMap from 'vue-amap';
Vue.use(VueAMap);
VueAMap.initAMapApiLoader({
  key: '00085a636239c8938dbef932ffe5c78d',
  plugin: ['AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.Autocomplete', 'Amap.PlaceSearch'],
  v: '1.4.15',
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
