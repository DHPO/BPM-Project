<template>
  <el-card id="wrap" shadow="hover">
    <div id="title">
      {{title}}
    </div>
    <div id="data" class="primary">
      {{value}}
    </div>
  </el-card>
</template>

<script lang="ts">
import {Component, Vue, Prop} from 'vue-property-decorator';

@Component({})
export default class StatisticShortcut extends Vue {
  @Prop()
  private title!: string;

  @Prop()
  private value!: number;
}
</script>

<style scoped>
#wrap {
  width: fit-content;
  float: left;
  margin: 10px;
  min-width: 150px;
}
#title {
  color: #606266;
}
#data {
  font-size: 32px;
}
.primary {
  color: #409EFF;
}
</style>