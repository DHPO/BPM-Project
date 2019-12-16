<template>
  <el-form id="form" label-position="left" label-width="100px" size="small">
    <el-form-item v-if="!loadCheckinUserSuccess" label="">
      <el-alert type="warning" show-icon :closable="false">
        <span slot="title">
          获取签到用户失败！&nbsp;
          <el-link type="danger" @click="getCheckinUsers">
            <i class="el-icon-refresh" />重试
          </el-link>
        </span>
      </el-alert>
    </el-form-item>
    <el-form-item label="页面操作">
      <el-button type="primary" @click="switchTab" :disabled="activeTabId === id">
        <i class="el-icon-s-promotion" />切换到此页面
      </el-button>
      <el-button
        type="danger"
        :disabled="activeTabId === id"
        @click="deleteTab"
        :loading="loading"
      >
        <i class="el-icon-delete" />删除此页面
      </el-button>
    </el-form-item>
    <el-form-item label="抽取人数">
      <el-input-number v-model="config.num" :min="1" :max="pool.length"></el-input-number>
    </el-form-item>
    <el-form-item label="中奖名单">
      <template v-if="showResultList">
        {{this.state.result.map(u => u.username).join(', ')}}
      </template>
      <span v-else>暂无</span>
    </el-form-item>
    <el-form-item label="抽奖操作">
      <el-button type="primary" v-show="showStartButton" :disabled="disableStartButton" @click="startLottery">开始抽奖</el-button>
      <el-button type="warning" v-show="showStopButton" :disabled="disableStopButton" @click="stopLottery">停止抽奖</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { apiErrorMessage } from '@/common/apiErrorMessage';
import * as interactiveAPI from '@/api/interactive';
import {
  InteractiveType,
  LotteryConfig,
  LotteryState,
  LotteryRunningState,
  LotteryPoolType,
} from '@/types/interactive';
import { ResultStatus } from '@/api/user';
import * as activityAPI from '@/api/activity';
import { User } from '../../../../types/user';
import { randChooseSome } from './utils';

@Component({})
export default class LotteryController extends Vue {
  @Prop({ required: true })
  private id!: string;

  @Prop({ required: true })
  private bus!: Vue;

  @Prop({ required: true })
  private config!: LotteryConfig;

  @Prop({ required: true })
  private activeTabId!: string;

  @Prop({ required: true })
  private activityId!: number;

  @Prop({ required: name })
  private name!: string;

  private dirty = false;
  private loading = false;
  private loadCheckinUserSuccess = false;

  private checkinUsers: User[] = [];

  get pool() {
    return this.checkinUsers;
  }

  get showStartButton() {
    return this.state.runningState === LotteryRunningState.Infant;
  }

  get showStopButton() {
    return this.state.runningState !== LotteryRunningState.Infant;
  }

  get showResultList() {
    return this.state.runningState === LotteryRunningState.Done;
  }

  get disableStartButton() {
    return this.pool.length === 0;
  }

  get disableStopButton() {
    return this.state.runningState === LotteryRunningState.Done;
  }

  private state: LotteryState = {
    runningState: LotteryRunningState.Infant,
  };

  private switchTab() {
    this.$emit('switchTab', this.id);
  }

  private startLottery() {
    this.state = {
      runningState: LotteryRunningState.Running,
      pool: this.pool,
    };
    this.updateState();
  }

  private stopLottery() {
    this.state = {
      runningState: LotteryRunningState.Done,
      result: randChooseSome(this.pool, this.config.num),
    };
    this.updateState();
  }

  private updateConfig() {
    const config = {
      id: this.id,
      name: this.name,
      type: InteractiveType.Lottery,
      config: this.config,
    };
    interactiveAPI
      .updateInteractive(this.activityId, config)
      .then((result) => {
        this.$message.success('修改已更新');
        this.dirty = false;
      })
      .catch((err: ResultStatus) => {
        this.dirty = true;
        apiErrorMessage(this, err);
      })
      .finally(() => {
        this.$emit('updateConfig', config);
      });
  }

  private updateState() {
    this.bus.$emit('updateState', {
      id: this.id,
      state: {
        ...this.state,
      },
    });
  }

  private deleteTab() {
    this.loading = true;
    interactiveAPI
      .deleteInteractive(this.id)
      .then(() => {
        this.$emit('deleteTab', this.id);
        this.$message.success('删除成功');
      })
      .catch((err) => {
        apiErrorMessage(this, err);
      })
      .finally(() => {
        this.loading = false;
      });
  }

  private getCheckinUsers() {
    activityAPI.getCheckinUsers(this.activityId)
      .then((users) => {
        this.checkinUsers = users;
        this.loadCheckinUserSuccess = true;
      })
      .catch((err) => {
        apiErrorMessage(this, err);
        this.loadCheckinUserSuccess = false;
      });
  }

  private mounted() {
    this.getCheckinUsers();
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
</style>
