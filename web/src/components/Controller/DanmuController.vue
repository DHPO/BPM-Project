<template>
  <div>
    <el-form id="form" label-position="left" label-width="100px" size="small">
      <el-form-item label="连接状态">
        <el-tag v-if="connectStatus" type="success">已连接</el-tag>
        <el-tag v-else type="danger">连接中断</el-tag>
      </el-form-item>
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
        <el-button type="primary" @click="sendTestDanmu">
          <i class="el-icon-chat-dot-square" />发送测试弹幕
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch, Prop } from 'vue-property-decorator';
import { BarrageDanmuConfig, DanmuMode } from '@/types/danmu';
// @ts-ignore
import socketio from 'socket.io-client';

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

  private socketioHandler: any;
  private activityId = 1575898405622;

  private connectStatus = false;

  @Watch('danmuMode')
  private handleDanmuModeChange(newVal: DanmuMode) {
    this.bus.$emit('setDanmuMode', newVal);
  }

  private sendTestDanmu() {
    this.bus.$emit('addDanmu', '测试弹幕');
  }

  @Watch('danmuSpeed')
  private handleDanmuSpeedChange(val: number) {
    this.bus.$emit('setDanmuConfig', { speed: val });
  }

  private mounted() {
    this.socketioHandler = socketio('http://10.0.0.86:9099');
    this.socketioHandler.on('connect', () => {
      this.connectStatus = true;
      this.socketioHandler.emit('comment_start', this.activityId);
    });
    this.socketioHandler.on('comment', (comment: any) => {
      this.bus.$emit('addDanmu', `${comment.username}: ${comment.content}`);
    });
    this.socketioHandler.on('disconnect', () => {
      this.connectStatus = false;
    });
    this.socketioHandler.on('reconnect', () => {
      this.connectStatus = true;
    });
  }
}
</script>

<style lang="css">
el-slider {
  width: 180px !important;
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
i::after {
  content: '　';
}
</style>