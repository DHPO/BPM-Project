<template>
  <el-table
    :data="questions"
    :row-class-name="shouldHighlight">
    <el-table-column
      type="index"
      label="序号"
      width="60"></el-table-column>
    <el-table-column
      label="内容"
      prop="text"></el-table-column>
    <el-table-column label="操作">
      <template slot="header">
        <el-button size="small">新建题目</el-button>
      </template>
      <template slot-scope="scope">
        <el-button size="small">编辑</el-button>
        <el-button size="small" type="danger" @click="deleteQuestion(scope)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts">
import {Component, Vue, Prop} from 'vue-property-decorator';
import { SlideContent } from '../../../../types/interactive';

@Component({})
export default class QuestionTable extends Vue {
  @Prop({required: true})
  private questions!: SlideContent;

  @Prop({required: true})
  private current!: number;

  // @ts-ignore
  private shouldHighlight({row, rowIndex}) {
    if (rowIndex === this.current) {
      return 'success-row';
    } else {
      return '';
    }
  }

  private deleteQuestion(scope: any) {
    this.$emit('deleteItem', scope.$index);
  }
}
</script>

<style>
  .el-table .success-row {
    background: #f0f9eb;
  }
</style>