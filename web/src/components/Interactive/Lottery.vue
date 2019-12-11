<template>
  <el-card class="wrapper">
    <template v-if="isInfant">
      <p class="center-text">幸运抽奖</p>
    </template>
    <div v-if="isRunning">
      <h1>正在抽奖...</h1>
      <p class="center-text">{{state.pool[currentIdx].username}}</p>
    </div>
    <div v-if="isDone">
      <h1>中奖名单</h1>
      <p class="center-text">{{state.result.map(u => u.username).join(', ')}}</p>
    </div>
  </el-card>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import {
  LotteryState,
  LotteryPoolType,
  LotteryRunningState,
  LotteryConfig,
} from '../../types/interactive';

@Component({})
export default class Lottery extends Vue {
  private currentIdx = 1;

  private intervalHandler!: any;

  @Prop({ required: true })
  private id!: string;

  @Prop({ required: true })
  private bus!: Vue;

  @Prop({ required: true })
  private config!: LotteryConfig;

  private state: LotteryState = {
    runningState: LotteryRunningState.Infant,
  };

  get pool() {
    return this.state.pool || [1];
  }

  get isInfant() {
    return this.state.runningState === LotteryRunningState.Infant;
  }

  get isRunning() {
    return this.state.runningState === LotteryRunningState.Running;
  }

  get isDone() {
    return this.state.runningState === LotteryRunningState.Done;
  }

  private updateState(payload: { id: string; state: LotteryState }) {
    if (payload.id === this.id) {
      this.state = { ...payload.state };
    }
  }

  private mounted() {
    this.intervalHandler = setInterval(() => {
      this.currentIdx = (this.currentIdx + 1) % this.pool.length;
    }, 50);
    this.bus.$on('updateState', this.updateState.bind(this));
  }

  private destroyed() {
    clearInterval(this.intervalHandler);
  }
}
</script>

<style scoped>
.wrapper {
  width: 60%;
  margin-left: auto;
  margin-right: auto;
  height: 70vh;
  margin-top: 15vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.center-text {
  font-size: 48px;
}
</style>