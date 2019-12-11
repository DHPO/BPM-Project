<template>
  <el-form id="form" label-position="left" label-width="100px" size="small">
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
    <el-form-item label="直播链接">
      {{config.url}}
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import * as interactiveAPI from '@/api/interactive';
import { apiErrorMessage } from '@/common/apiErrorMessage';
import { VideoConfig } from '../../../types/interactive';

@Component({})
export default class SigninController extends Vue {
  @Prop({ required: true })
  private id!: string;

  @Prop({ required: true })
  private bus!: Vue;

  @Prop({ required: true })
  private config!: VideoConfig;

  @Prop({ required: true })
  private activeTabId!: string;

  @Prop({ required: true })
  private activityId!: number;

  @Prop({ required: name })
  private name!: string;

  private loading = false;

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
