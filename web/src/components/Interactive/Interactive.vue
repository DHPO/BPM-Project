<template>
  <div style="position: absolute;">
    <barrage id="barrage" v-show="enableBarrage" :bus="bus" />
    <div id="main">
      <InteractiveWrapper
        v-for="config in tabs"
        :key="config.id"
        v-show="config.id===activeTabId"
        :bus="bus"
        :interactiveConfig="config"
      />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import barrage from '../danmu/barrage.vue';
import Questionnaire from '../Questionnaire/Questionnaire.vue';
import { DanmuMode } from '@/types/danmu';
import { InteractiveConfig, InteractiveType } from '../../types/interactive';
import InteractiveWrapper from './InteractiveWrapper.vue';

const defaultTabs = [
  {
    id: 'signin',
    name: '签到',
    type: InteractiveType.Signin,
    config: {
      url: 'https://www.baidu.com',
    },
  },
];

@Component({
  components: {
    barrage,
    Questionnaire,
    InteractiveWrapper,
  },
})
export default class Interactive extends Vue {
  private count: number = 0;
  private intervalHandler!: number;
  private bus: Vue = new Vue();

  private danmuMode: DanmuMode = 'barrage';

  private tabs: Array<InteractiveConfig<any>> = [...defaultTabs];

  private activeTabId: string = defaultTabs[0].id;

  private receiveEvent() {
    if (localStorage.getItem('events') === null) {
      return [];
    }
    const events = JSON.parse(localStorage.getItem('events')!) as any[];
    localStorage.setItem('events', '[]');
    events.forEach((e) => {
      this.bus.$emit(e.type, e.payload);
    });
    return events;
  }

  get enableBarrage() {
    return this.danmuMode === 'barrage';
  }

  private createTab(config: InteractiveConfig<any>) {
    this.tabs.push(config);
  }

  private mounted() {
    this.intervalHandler = setInterval(this.receiveEvent.bind(this), 200);
    this.bus.$on('count', (c: any) => (this.count = c));
    this.bus.$on('setDanmuMode', (mode: DanmuMode) => (this.danmuMode = mode));
    this.bus.$on('createTab', this.createTab.bind(this));
    this.bus.$on('switchTab', (id: string) => (this.activeTabId = id));
    this.bus.$on('syncTab', (tabs: any) => (this.tabs = tabs));
  }

  private unmounted() {
    clearInterval(this.intervalHandler);
  }
}
</script>

<style lang="css" scoped>
#main {
  position: fixed;
  top: 0px;
  left: 0px;
  width: 100%;
}

#barrage {
  z-index: 10000 !important;
}
</style>
