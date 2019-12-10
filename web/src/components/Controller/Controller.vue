<template>
  <div>
    <div style="height:5vh;"></div>
    <el-card class="card">
      <h1>屏幕控制面板</h1>
      <div style="height:30px;"></div>
      <el-divider class="divider" content-position="left">弹幕设置</el-divider>
      <DanmuController class="controller-item" :bus="bus"/>
      <el-divider class="divider" content-position="left">页面设置</el-divider>
      <TabController class="controller-item" :bus="bus"/>
    </el-card>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Watch} from 'vue-property-decorator';
import DanmuController from './DanmuController.vue';
import TabController from './TabController.vue';
import {EventType} from '@/types/event';

@Component({
  components: {
    DanmuController,
    TabController,
  },
})
export default class Controller extends Vue {
  private count: number = 0;
  private enableDanmu: boolean = true;
  private bus: Vue = new Vue();

  private sendCount() {
    this.sendEvent({
      type: 'addDanmu',
      payload: this.count,
    });
    this.count += 1;
  }

  private sendEvent<T>(event: EventType<T>) {
    const events = JSON.parse(localStorage.getItem('events') || '[]') as any[];
    events.push(event);
    localStorage.setItem('events', JSON.stringify(events));
  }

  @Watch('enableDanmu')
  private handleEnableDanmu(newVal: boolean) {
    this.sendEvent({
      type: 'setDanmuMode',
      payload: newVal ? 'barrage' : 'off',
    });
  }

  private redirectEvents(type: string, payload: any) {
    this.sendEvent({
      type,
      payload,
    });
  }

  private bind(eventType: string) {
    this.bus.$on(eventType, (p: any) => this.redirectEvents(eventType, p));
  }

  private mounted() {
    this.bind('setDanmuMode');
    this.bind('addDanmu');
    this.bind('setDanmuConfig');
    this.bind('createTab');
    this.bind('switchTab');
    this.bind('syncTab');
    this.bind('updateState');
  }
}
</script>

<style lang="css" scoped>
  .card {
    width: 60%;
    margin-left: auto;
    margin-right: auto;
    padding: 0px 50px;
    height: 90vh;
    overflow-y: auto;
  }
  .controller-item {
    margin: 0px 5vh;
  }
</style>

<style lang="css">
  .divider>div {
    font-size: 25px !important;
    font-weight: bold;
  }
</style>