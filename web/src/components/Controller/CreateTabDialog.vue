<template>
  <el-dialog :visible.sync="dialogVisible" title="新建页面">
    <el-form id="form" label-position="left" label-width="100px" size="small">
      <el-form-item label="页面类型">
        <el-select v-model="type" placeholder="请选择页面类型">
          <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="页面名称">
        <el-input v-model="name" placeholder="请输入页面名称"></el-input>
      </el-form-item>
      <!--问卷-->
      <template v-if="showQuestionnare">
        <el-form-item label="问卷星ID">
          <el-input v-model="questionnaireId" placeholder="请输入问卷星ID"></el-input>
        </el-form-item>
      </template>
      <el-form-item>
        <el-button type="primary" @click="submit" :disabled="!submitable">确定</el-button>
        <el-button @click="dialogVisible=false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import {InteractiveType, QuestionnaireConfig, InteractiveConfig} from '@/types/interactive';

const options = [
  {
    value: InteractiveType.Questionnaire,
    label: '问卷',
  },
];

@Component({})
export default class CreateTabDialog extends Vue {
  private dialogVisible: boolean = false;

  private options = options;

  private type: InteractiveType = InteractiveType.Questionnaire;
  private name: string = '';
  private questionnaireId: string = '';

  public show() {
    this.dialogVisible = true;
  }

  get submitable() {
    if (this.name === '') {
      return false;
    }
    switch (this.type) {
      case InteractiveType.Questionnaire: {
        return this.questionnaireId !== '';
      }
    }
  }

  get showQuestionnare() {
    return this.type === InteractiveType.Questionnaire;
  }

  public submit() {
    let config;
    switch (this.type) {
      case InteractiveType.Questionnaire: {
        config = {
          id: `${this.type}_${Date.now()}`,
          type: this.type,
          name: this.name,
          config: {
            id: this.questionnaireId,
          },
        } as InteractiveConfig<QuestionnaireConfig>;
      }
    }
    this.$emit('submit', config);
    this.dialogVisible = false;
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
    padding-right: 20px;
    float: left;
  }
  #form {
    overflow: auto;
  }
  div.el-select {
    width: 100% !important;
  }
</style>