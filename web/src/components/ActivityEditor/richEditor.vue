<template>
  <el-tabs id="tab" v-model="activeName" type="card">
    <el-tab-pane label="编辑" name="edit">
      <div id="wrapper">
        <quill-editor id="editor" ref="quill" :options="editorOption" v-model="content"/>
      </div>
    </el-tab-pane>
    <el-tab-pane label="预览" name="preview">
      <div style="text-align: left" v-html="content" />
    </el-tab-pane>
  </el-tabs>
</template>

<script lang="ts">
// @ts-ignore
import { quillEditor, Quill } from 'vue-quill-editor';
// @ts-ignore
import { container, ImageExtend, QuillWatch } from 'quill-image-extend-module';
// @ts-ignore
import ImageResize from 'quill-image-resize-module';
import { Component, Vue, Ref, Watch } from 'vue-property-decorator';

import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';

Quill.register('modules/ImageExtend', ImageExtend);
Quill.register('modules/ImageResize', ImageResize);

import { uploadRichText } from '@/api/file';

@Component({
  components: {
    quillEditor,
  },
})
export default class Editor extends Vue {
  @Ref('quill')
  private quill: any;

  private updateUrl = '/api/photo';

  private content = '';

  private activeName = 'edit';

  private editorOption = {
    theme: 'snow',
    placeholder: '请输入活动详细说明',
    modules: {
      ImageResize: {},
      ImageExtend: {
        loading: true,
        name: 'img',
        action: this.updateUrl,
        response: (res: any) => {
          return res.data;
        },
      },
      toolbar: {
        container,
        handlers: {
          image() {
            // @ts-ignore
            QuillWatch.emit(this.quill.id);
          },
        },
      },
    },
  };

  @Watch('content')
  private onContentChange(newVal: string) {
    this.$emit('submit', newVal);
  }
}
</script>

<style scoped>
#wrapper {
  background-color: white;
  height: 500px;
}
#editor {
  height: 500px;
}

#tab {
  background-color: white;
}
</style>

<style>
#tab>.el-tabs__header{
  margin-bottom: 0px;
}

.editor {
  line-height: normal !important;
  height: 800px;
}
.ql-snow .ql-tooltip[data-mode=link]::before {
  content: "请输入链接地址:";
}
.ql-snow .ql-tooltip.ql-editing a.ql-action::after {
    border-right: 0px;
    content: '保存';
    padding-right: 0px;
}

.ql-snow .ql-tooltip[data-mode=video]::before {
    content: "请输入视频地址:";
}

.ql-snow .ql-picker.ql-size .ql-picker-label::before,
.ql-snow .ql-picker.ql-size .ql-picker-item::before {
  content: '14px';
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value=small]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value=small]::before {
  content: '10px';
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value=large]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value=large]::before {
  content: '18px';
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value=huge]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value=huge]::before {
  content: '32px';
}

.ql-snow .ql-picker.ql-header .ql-picker-label::before,
.ql-snow .ql-picker.ql-header .ql-picker-item::before {
  content: '文本';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
  content: '标题1';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
  content: '标题2';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
  content: '标题3';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
  content: '标题4';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
  content: '标题5';
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
  content: '标题6';
}

.ql-snow .ql-picker.ql-font .ql-picker-label::before,
.ql-snow .ql-picker.ql-font .ql-picker-item::before {
  content: '标准字体';
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value=serif]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value=serif]::before {
  content: '衬线字体';
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value=monospace]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value=monospace]::before {
  content: '等宽字体';
}
</style>