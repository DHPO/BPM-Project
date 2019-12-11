<template>
  <el-row type="flex" justify="center">
    <el-col v-if="state.showQrCode" class="wrapper" :span="6">
      <div>
        <h1>投票</h1>
        <VueQr id="qrcode" :text="qrcodeText" :size="300"/>
        <p>请扫描二维码参与投票</p>
      </div>
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

  get qrcodeText() {
    return JSON.stringify({
      type: 'vote',
      url: this.questionnareUrl,
    });
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
    width: 100%;
  }

  #qrcode-padding {
    height: 30vh !important;
  }

  p {
    text-align: center;
    font-size: 20px;
  }

  .wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }

  h1 {
    font-size: 48px;
  }
</style>