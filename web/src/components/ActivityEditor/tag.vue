<template>
  <div id="wrapper">
    <el-tag
      :key="tag"
      v-for="tag in dynamicTags"
      closable
      :disable-transitions="false"
      @close="handleClose(tag)"
    >{{tag}}</el-tag>
    <el-input
      class="input-new-tag"
      v-if="inputVisible"
      v-model="inputValue"
      ref="saveTagInput"
      size="small"
      @keyup.enter.native="handleInputConfirm"
      @blur="handleInputConfirm"
    ></el-input>
    <el-button
      v-else
      v-show="dynamicTags.length < 5"
      class="button-new-tag"
      size="small"
      @click="showInput"
    >+ 增加标签</el-button>
  </div>
</template>

<style>
.el-tag {
  margin-right: 10px;
  margin-bottom: 10px;
}
.button-new-tag {
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>

<style scoped>
#wrapper {
  text-align: left;
}
</style>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component({})
export default class Tags extends Vue {
  private dynamicTags: string[] = [];
  private inputVisible = false;
  private inputValue = '';

  @Prop({default: []})
  private tags!: string[];

  public handleClose(tag: string) {
    this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
  }

  public showInput() {
    this.inputVisible = true;
    this.$nextTick(() => {
      (this.$refs.saveTagInput as any).$refs.input.focus();
    });
  }

  public handleInputConfirm() {
    const inputValue = this.inputValue;
    if (inputValue) {
      this.dynamicTags.push(inputValue);
      this.$emit('submit', this.dynamicTags);
    }
    this.inputVisible = false;
    this.inputValue = '';
  }

  private created() {
    this.dynamicTags = this.tags;
  }
}
</script>
