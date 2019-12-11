<template>
  <el-form id="form" label-position="left" label-width="100px" size="small">
    <el-form-item label="页面操作">
      <el-button type="primary" @click="switchTab" :disabled="activeTabId === id"><i class="el-icon-s-promotion"/>切换到此页面</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component({})
export default class SigninController extends Vue {
  @Prop({ required: true })
  private id!: string;

  @Prop({ required: true })
  private bus!: Vue;

  @Prop({ required: true })
  private activeTabId!: string;

  private switchTab() {
    this.$emit('switchTab', this.id);
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
}
#form {
  overflow: auto;
}
</style>
