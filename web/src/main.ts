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

// @ts-ignore
import ECharts from 'vue-echarts'; // refers to components/ECharts.vue in webpack

import 'echarts/lib/chart/bar';
import 'echarts/lib/chart/line';
import 'echarts/lib/chart/pie';
import 'echarts/lib/chart/map';
import 'echarts/lib/chart/radar';
import 'echarts/lib/chart/scatter';
import 'echarts/lib/chart/effectScatter';
import 'echarts/lib/component/tooltip';
import 'echarts/lib/component/polar';
import 'echarts/lib/component/geo';
import 'echarts/lib/component/legend';
import 'echarts/lib/component/title';
import 'echarts/lib/component/visualMap';
import 'echarts/lib/component/markPoint';
// register component to use
Vue.component('v-chart', ECharts);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
