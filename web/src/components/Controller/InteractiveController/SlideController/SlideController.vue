<template>
  <el-form id="form-slide" label-position="left" label-width="100px" size="small">
    <el-form-item label="操作">
      <el-button
        type="primary"
        @click="switchTab"
        :disabled="activeTabId === id">切换到此页面</el-button>
      <el-button
        type="danger"
        :disabled="activeTabId === id">删除此页面</el-button>
    </el-form-item>
    <el-form-item label="题目显示">
      <el-input-number
        v-model="currentP1"
        :min="0"
        :max="config.questions.length"></el-input-number>
    </el-form-item>
    <el-form-item class='wide' label="题目列表">
      <QuestionTable
        :questions="config.questions"
        :current="state.current"
        @createItem="createHandler"
        @editItem="editHandler"
        @deleteItem="deleteHandler"/>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import {Component, Vue, Prop, Watch} from 'vue-property-decorator';
import { SlideConfig, SlideState, SlideContent } from '../../../../types/interactive';
import QuestionTable from './QuestionTable.vue';

@Component({
  components: {
    QuestionTable,
  },
})
export default class SlideController extends Vue {
  @Prop({required: true})
  private id!: string;

  @Prop({required: true})
  private bus!: Vue;

  @Prop({required: true})
  private config!: SlideConfig;

  @Prop({required: true})
  private activeTabId!: string;

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
    this.$emit('updateConfig', {
      id: this.id,
      config: this.config,
    });
    this.$nextTick(() => this.updateState);
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

  private editHandler({idx, data}: {idx: number, data: SlideContent}) {
    Vue.set(this.config.questions, idx, data);
    this.updateConfig();
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
  #form-slide {
    overflow: auto;
    height: 450px;
  }
  div.el-form-item.wide {
    width: 90%;
    float: left;
  }
</style>
