<template>
  <div>
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
          <el-button size="small" @click="createQuestion">新建题目</el-button>
        </template>
        <template slot-scope="scope">
          <el-button size="small" @click="editQuestion(scope)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteQuestion(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <QuestionEditor
      ref="editor"
      @create="createHandler"
      @edit="editHandler"
    />
  </div>
</template>

<script lang="ts">
import {Component, Vue, Prop, Ref} from 'vue-property-decorator';
import { SlideContent } from '@/types/interactive';
import QuestionEditor from './QuestionEditor.vue';

@Component({
  components: {
    QuestionEditor,
  },
})
export default class QuestionTable extends Vue {
  @Prop({required: true})
  private questions!: SlideContent;

  @Prop({required: true})
  private current!: number;

  @Ref('editor')
  private editor!: QuestionEditor;

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

  private createQuestion() {
    this.editor.createQuestion();
  }

  private editQuestion(scope: any) {
    this.editor.editQuestion(scope.$index, scope.row.text);
  }

  private createHandler(payload: any) {
    this.$emit('createItem', payload);
  }

  private editHandler(payload: any) {
    this.$emit('editItem', payload);
  }
}
</script>

<style>
  .el-table .success-row {
    background: #f0f9eb;
  }
</style>