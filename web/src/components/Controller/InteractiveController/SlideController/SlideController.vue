<template>
  <el-form id="form-slide" label-position="left" label-width="100px" size="small">
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
    <el-form-item label="题目显示">
      <el-input-number v-model="currentP1" :min="0" :max="config.questions.length"></el-input-number>
    </el-form-item>
    <el-form-item class="wide" label="题目列表">
      <el-alert type="warning" v-show="dirty" show-icon :closable="false">
        <span slot="title">
          有修改未同步至服务器&nbsp;
          <el-link type="warning" @click="updateConfig">
            <i class="el-icon-refresh" />立即同步
          </el-link>
        </span>
      </el-alert>
      <QuestionTable
        :questions="config.questions"
        :current="state.current"
        @createItem="createHandler"
        @editItem="editHandler"
        @deleteItem="deleteHandler"
      />
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { Component, Vue, Prop, Watch } from 'vue-property-decorator';
import {
  SlideConfig,
  SlideState,
  SlideContent,
  InteractiveType,
} from '../../../../types/interactive';
import QuestionTable from './QuestionTable.vue';
import * as interactiveAPI from '@/api/interactive';
import { ResultStatus } from '../../../../api/user';
import { apiErrorMessage } from '../../../../common/apiErrorMessage';

@Component({
  components: {
    QuestionTable,
  },
})
export default class SlideController extends Vue {
  @Prop({ required: true })
  private id!: string;

  @Prop({ required: true })
  private bus!: Vue;

  @Prop({ required: true })
  private config!: SlideConfig;

  @Prop({ required: true })
  private activeTabId!: string;

  @Prop({ required: true })
  private activityId!: number;

  @Prop({ required: name })
  private name!: string;

  private loading = false;

  private dirty: boolean = false;

  private state: SlideState = {
    current: -1,
  };

  private currentP1 = 0;

  private updateState() {
    this.bus.$emit('updateState', {
      id: this.id,
      state: {
        ...this.state,
      },
    });
  }

  @Watch('currentP1')
  private handleStateChange() {
    this.state.current = this.currentP1 - 1;
    this.updateState();
  }

  private switchTab() {
    this.$emit('switchTab', this.id);
  }

  private updateConfig() {
    const config = {
      id: this.id,
      name: this.name,
      type: InteractiveType.Slide,
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

  private deleteHandler(idx: number) {
    this.config.questions.splice(idx, 1);
    Vue.set(this.config, 'questions', this.config.questions);
    this.updateConfig();
  }

  private createHandler(data: SlideContent) {
    this.config.questions.push(data);
    Vue.set(this.config, 'questions', this.config.questions);
    this.updateConfig();
  }

  private editHandler({ idx, data }: { idx: number; data: SlideContent }) {
    Vue.set(this.config.questions, idx, data);
    this.updateConfig();
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
#form-slide {
  overflow: auto;
  height: 450px;
}
div.el-form-item.wide {
  width: 90%;
  float: left;
}
</style>
