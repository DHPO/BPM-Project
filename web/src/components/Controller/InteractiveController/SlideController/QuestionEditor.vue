<template>
  <el-dialog :visible.sync="visible" title="编辑题目">
    <el-form id="form" label-position="left" label-width="100px" size="small">
      <el-form-item
        label="题目内容">
          <el-input v-model="content"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">确定</el-button>
        <el-button @click="visible=false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import { SlideContent } from '../../../../types/interactive';

enum Mode {
  Create,
  Edit,
}

@Component({})
export default class QuestionEditor extends Vue {
  private visible = false;
  private mode: Mode = Mode.Create;

  private idx?: number;

  private content = '';

  public createQuestion() {
    this.mode = Mode.Create;
    this.content = '';
    this.visible = true;
  }

  public editQuestion(idx: number, content: string) {
    this.mode = Mode.Edit;
    this.idx = idx;
    this.content = content;
    this.visible = true;
  }

  private submit() {
    if (this.mode === Mode.Create) {
      this.$emit('create', {
        text: this.content
      } as SlideContent);
      this.content = '';
    } else {
      this.$emit('edit', {
        idx: this.idx,
        data: {
          text: this.content
        } as SlideContent
      })
    }
    this.visible = false;
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