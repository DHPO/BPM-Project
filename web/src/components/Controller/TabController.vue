<template>
  <div>
    <el-form id="form" label-position="left" label-width="100px" size="small">
      <el-form-item label="当前页面">
        <el-select v-model="activeTabId">
          <el-option
          v-for="tab in tabs"
          :key="tab.id"
          :value="tab.id"
          :label="tab.name"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="操作">
        <el-button type="primary" @click="showCreateTabDialog">新建页面</el-button>
        <el-button type="primary" @click="syncTab">同步页面设置</el-button>
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeTab" type="card">
      <el-tab-pane
      v-for="tab in tabs"
      :key="tab.id"
      :name="tab.id"
      :label="tab.name">
        <SigninController
          v-if="isSignin(tab.type)"
          :id="tab.id"
          :bus="bus"
          :activeTabId="activeTabId"
          @switchTab="switchTab"/>
        <QuestionnaireController
          v-if="isQuestionnaire(tab.type)"
          :id="tab.id"
          :bus="bus"
          :config="tab.config"
          :activeTabId="activeTabId"
          @switchTab="switchTab"/>
        <SliderController
          v-if="isSlide(tab.type)"
          :id="tab.id"
          :bus="bus"
          :config="tab.config"
          :activeTabId="activeTabId"
          @updateConfig="updateConfig"
          @switchTab="switchTab"/>
      </el-tab-pane>
    </el-tabs>
    <CreateTabDialog ref="createTabDialog" @submit="createNewTab"/>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Prop, Ref, Watch} from 'vue-property-decorator';
import {InteractiveType} from '@/types/interactive';
import CreateTabDialog from './CreateTabDialog.vue';
import QuestionnaireController from './InteractiveController/QuestionnaireController.vue';
import SigninController from './InteractiveController/SigninController.vue';
import SliderController from './InteractiveController/SlideController/SlideController.vue';

@Component({
  components: {
    CreateTabDialog,
    QuestionnaireController,
    SigninController,
    SliderController,
  },
})
export default class TabController extends Vue {
  @Prop(Vue)
  private bus!: Vue;

  @Ref('createTabDialog')
  private createTabDialog!: CreateTabDialog;

  private activeTab: string = 'signin';
  private activeTabId: string = 'signin';

  private tabs = [
    {
      id: 'signin',
      name: '签到',
      type: InteractiveType.Signin,
      config: {
        url: 'http://www.baidu.com',
      },
    },
  ];

  private showCreateTabDialog() {
    this.createTabDialog.show();
  }

  private createNewTab(config: any) {
    this.tabs.push(config);
    this.bus.$emit('createTab', config);
    this.bus.$emit('switchTab', this.activeTabId);
  }

  private syncTab() {
    this.bus.$emit('syncTab', this.tabs);
    this.bus.$emit('switchTab', this.activeTabId);
  }

  @Watch('activeTabId')
  private handleActiveTabChange(val: string) {
    this.bus.$emit('switchTab', val);
  }

  private isQuestionnaire(type: InteractiveType) {
    return type === InteractiveType.Questionnaire;
  }

  private isSignin(type: InteractiveType) {
    return type === InteractiveType.Signin;
  }

  private isSlide(type: InteractiveType) {
    return type === InteractiveType.Slide;
  }

  private switchTab(id: string) {
    this.activeTabId = id;
  }

  private updateConfig(payload: {id: string; config: any}) {
    const tab = this.tabs.find((t) => t.id === payload.id);
    tab!.config = {
      ...tab!.config,
      ...payload.config,
    };
    this.syncTab();
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
    height: 35px;
    margin-left: 40px;
  }
  #form {
    overflow: auto;
  }
</style>