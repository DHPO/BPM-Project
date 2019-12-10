<template>
  <div>
    <el-form id="form" label-position="left" label-width="100px" size="small">
      <el-form-item label="显示方式">
        <el-radio-group v-model="danmuMode">
          <el-radio-button label="off">关闭</el-radio-button>
          <el-radio-button label="barrage">全屏弹幕</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="弹幕速度">
        <el-slider class="slider" v-model="danmuSpeed" :min="1" :max="10"></el-slider>
      </el-form-item>
      <el-form-item label="操作">
        <el-button type="primary" @click="sendTestDanmu">发送测试弹幕</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Watch, Prop} from 'vue-property-decorator';
import {BarrageDanmuConfig, DanmuMode} from '@/types/danmu';

const defaultBarrageDanmuConfig: BarrageDanmuConfig = {
  speed: 5,
  size: 24,
};

@Component({})
export default class DanmuController extends Vue {
  @Prop(Vue)
  private bus!: Vue;

  private danmuMode: DanmuMode = 'barrage';
  private danmuSpeed: number = 5;

  @Watch('danmuMode')
  private handleDanmuModeChange(newVal: DanmuMode) {
    this.bus.$emit('setDanmuMode', newVal);
  }

  private sendTestDanmu() {
    this.bus.$emit('addDanmu', '测试弹幕');
  }

  @Watch('danmuSpeed')
  private handleDanmuSpeedChange(val: number) {
    this.bus.$emit('setDanmuConfig', {speed: val});
  }
}
</script>

<style lang="css">
  el-slider {
    width:180px !important;
    margin-left: auto;
    margin-right: auto;
  }
  div.el-form-item {
    width: 400px;
    float: left;
  }
  #form {
    overflow: auto;
  }
</style>