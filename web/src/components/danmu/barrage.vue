<template>
  <div>
    <div ref="container" id="container"></div>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Ref, Prop} from 'vue-property-decorator';
import { BarrageDanmuConfig } from '../../types/danmu';

@Component({})
export default class BarrageDanmu extends Vue {
  @Ref('container')
  private container: any;

  @Prop(Vue)
  private bus!: Vue;

  private send!: (danmu: any) => void;
  private $start: any;

  private danmuConfig: BarrageDanmuConfig = {};

  private initBarrage() {
    this.send = this.$start(this.container, [0, 1]);
  }

  public addDanmu(text: string) {
    this.send({
      ...this.danmuConfig,
      text,
    });
  }

  private mounted() {
    this.initBarrage();
    this.bus.$on('addDanmu', (text: string) => this.addDanmu(text));
    this.bus.$on('setDanmuConfig', (config: any) => {
      this.danmuConfig = {...this.danmuConfig, ...config};
    });
  }

}
</script>

<style lang="css" scoped>
  #container {
    position: fixed;
    top: 0px;
    left: 0px;
    height: 100vh;
    width: 100vw;
  }
</style>

<style lang="css">
  .barrage {
    font-size: 24px;
    color: white;
    background-color: #000000AA;
    border-radius: 50px 50px 50px 50px !important;
  }
</style>