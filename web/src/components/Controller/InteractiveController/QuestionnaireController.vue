<template>
  <el-form id="form" label-position="left" label-width="100px" size="small">
    <el-form-item label="操作">
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
    <el-form-item label="问卷星ID">{{config.id}}</el-form-item>
    <el-form-item label="显示设置">
      <el-checkbox label="显示二维码" v-model="currentState.showQrCode"></el-checkbox>
      <el-checkbox label="显示结果" v-model="currentState.showResult"></el-checkbox>
    </el-form-item>
    <el-form-item label="自动刷新">
      <el-checkbox label="自动刷新调查结果" v-model="currentState.reload"></el-checkbox>
    </el-form-item>
    <el-form-item label>
      <el-button type="primary" @click="updateState">
        <i class="el-icon-refresh" />更新设置
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import { QuestionnaireState, QuestionnaireConfig } from '@/types/interactive';
import * as interactiveAPI from '@/api/interactive';
import { apiErrorMessage } from '@/common/apiErrorMessage';

@Component({})
export default class QuestionnaireController extends Vue {
  @Prop({ required: true })
  private id!: string;

  @Prop({ required: true })
  private config!: QuestionnaireConfig;

  @Prop({ required: true })
  private bus!: Vue;

  @Prop({ required: true })
  private activeTabId!: string;

  private loading = false;

  private currentState: QuestionnaireState = {
    showQrCode: true,
    showResult: false,
    reload: true,
  };

  private lastSendState: QuestionnaireState = {
    showQrCode: true,
    showResult: false,
    reload: true,
  };

  get dirty() {
    for (const k in this.currentState) {
      if ((this.currentState as any)[k] !== (this.lastSendState as any)[k]) {
        return true;
      }
    }
    return false;
  }

  private updateState() {
    this.lastSendState = { ...this.currentState };
    this.bus.$emit('updateState', {
      id: this.id,
      state: {
        ...this.currentState,
      },
    });
  }

  private switchTab() {
    this.$emit('switchTab', this.id);
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
