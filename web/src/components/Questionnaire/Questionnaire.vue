<template>
  <el-row type="flex" justify="center">
    <el-col v-if="state.showQrCode" :span="6">
      <div id="qrcode-padding"></div>
      <VueQr id="qrcode" :text="questionnareUrl" :size="300"/>
      <p>请扫描二维码参与调查</p>
    </el-col>
    <el-col v-if="state.showResult" :span="18" id="result-wrapper">
      <QuestionnaireResult id="Questionaire" :url="questionnareResultUrl" :enableReload="state.reload"/>
    </el-col>
  </el-row>
</template>

<script lang="ts">
import {Component, Vue, Prop} from 'vue-property-decorator';
import QuestionnaireResult from './QuestionnaireResult.vue';
// @ts-ignore
import VueQr from 'vue-qr';
import { QuestionnaireState, StateRequest } from '../../types/interactive';

@Component({
  components: {
    QuestionnaireResult,
    VueQr,
  },
})
export default class Questionnare extends Vue {
  @Prop({required: true})
  private questionnaireId!: number;

  @Prop({required: true})
  private bus!: Vue;

  @Prop({required: true, type: String})
  private id!: string;

  private state: QuestionnaireState = {
    showQrCode: true,
    showResult: false,
    reload: true,
  };

  get questionnareUrl() {
    return `https://www.wjx.cn/jq/${this.questionnaireId}.aspx`;
  }

  get questionnareResultUrl() {
    return `https://www.wjx.cn/report/${this.questionnaireId}.aspx`;
  }

  private updateState(state: StateRequest) {
    if (state.id === this.id) {
      this.state = {...state.state};
    }
  }

  private mounted() {
    this.bus.$on('updateState', this.updateState.bind(this));
  }
}
</script>

<style lang="css" scoped>
  #result-wrapper, #Questionaire {
    height: 100vh !important;
    overflow-y: auto;
    overflow-x: auto;
  }

  #qrcode-padding {
    height: 30vh !important;
  }

  p {
    text-align: center;
  }
</style>