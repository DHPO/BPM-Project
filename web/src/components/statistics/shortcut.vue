<template>
  <el-card id="wrap" shadow="hover">
    <div id="title">
      {{title}}
    </div>
    <div v-if="isPositive" id="data" class="danger">
      <i class="el-icon-caret-top"/>
      {{(100 * absValue).toFixed(1)}} %
    </div>
    <div v-else id="data" class="success">
      <i class="el-icon-caret-bottom"/>
      {{(100 * absValue).toFixed(1)}} %
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

  get absValue() {
    return this.value > 0 ? Math.min(9.99, this.value) : -this.value;
  }

  get isPositive() {
    return this.value >= 0;
  }
}
</script>

<style scoped>
#wrap {
  width: 350px;
  float: left;
  margin: 10px;
}
#title {
  color: #606266;
}
#data {
  font-size: 40px;
}
.success {
  color: #67C23A;
}
.danger {
  color: #F56C6C;
}
i::after {
  content: '' !important;
}
</style>