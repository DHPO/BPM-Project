<template>
  <div>
    <el-form id="form" label-position="left" label-width="100px" size="small">
      <el-form-item label="当前页面">
        <el-select v-model="activeTabId">
          <el-option v-for="tab in tabs" :key="tab.id" :value="tab.id" :label="tab.name"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="操作">
        <el-button type="primary" @click="showCreateTabDialog">
          <i class="el-icon-document" />新建页面
        </el-button>
        <el-button @click="syncTab">
          <i class="el-icon-refresh" />同步页面设置
        </el-button>
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeTab" type="card">
      <el-tab-pane v-for="tab in tabs" :key="tab.id" :name="tab.id" :label="tab.name">
        <SigninController
          v-if="isSignin(tab.type)"
          :id="tab.id"
          :bus="bus"
          :name="tab.name"
          :activeTabId="activeTabId"
          :activityId="activityId"
          @switchTab="switchTab"
        />
        <QuestionnaireController
          v-if="isQuestionnaire(tab.type)"
          :id="tab.id"
          :bus="bus"
          :name="tab.name"
          :config="tab.config"
          :activeTabId="activeTabId"
          :activityId="activityId"
          @switchTab="switchTab"
          @deleteTab="deleteTab"
        />
        <SliderController
          v-if="isSlide(tab.type)"
          :id="tab.id"
          :bus="bus"
          :name="tab.name"
          :config="tab.config"
          :activeTabId="activeTabId"
          :activityId="activityId"
          @updateConfig="updateConfig"
          @switchTab="switchTab"
          @deleteTab="deleteTab"
        />
      </el-tab-pane>
    </el-tabs>
    <CreateTabDialog :activityId="activityId" ref="createTabDialog" @submit="createNewTab" />
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop, Ref, Watch } from 'vue-property-decorator';
import { InteractiveType, InteractiveConfig } from '@/types/interactive';
import CreateTabDialog from './CreateTabDialog.vue';
import QuestionnaireController from './InteractiveController/QuestionnaireController.vue';
import SigninController from './InteractiveController/SigninController.vue';
import SliderController from './InteractiveController/SlideController/SlideController.vue';
import * as interactiveAPI from '@/api/interactive';
import { ResultStatus } from '../../api/user';
import { apiErrorMessage } from '../../common/apiErrorMessage';

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

  private activityId = 1575898405622;

  private get tabs() {
    return [
      {
        id: 'signin',
        name: '签到',
        type: InteractiveType.Signin,
        config: {
          url: 'http://www.baidu.com',
        },
      },
      ...this.interactives,
    ];
  }

  private interactives: Array<InteractiveConfig<any>> = [];

  private showCreateTabDialog() {
    this.createTabDialog.show();
  }

  private createNewTab(config: any) {
    this.interactives.push(config);
    this.bus.$emit('createTab', config);
    this.bus.$emit('switchTab', this.activeTabId);
  }

  private deleteTab(id: string) {
    const idx = this.interactives.findIndex((i) => i.id === id);
    this.interactives.splice(idx, 1);
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

  private updateConfig(payload: { id: string; config: any }) {
    const tab = this.interactives.find((t) => t.id === payload.id);
    tab!.config = {
      ...tab!.config,
      ...payload.config,
    };
    this.syncTab();
  }

  private loadInteractives() {
    interactiveAPI
      .getInteractive(this.activityId)
      .then((interactives) => {
        this.interactives = interactives;
        this.syncTab();
        this.$message.success('加载成功');
      })
      .catch((err: ResultStatus) => {
        apiErrorMessage(this, err);
      });
  }

  private mounted() {
    this.loadInteractives();
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
  height: 35px;
  margin-left: 40px;
}
#form {
  overflow: auto;
}
</style>