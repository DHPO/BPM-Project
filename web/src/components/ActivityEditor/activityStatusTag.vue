<template>
  <el-tag :type="type" :effect="effect">{{text}}</el-tag>
</template>

<script lang="ts">
import {Component, Vue, Prop} from 'vue-property-decorator';
import { ActivityStatus } from '../../types/activity';

@Component({})
export default class StatusTag extends Vue {
  @Prop()
  private status!: ActivityStatus;

  get type() {
    switch (this.status) {
      case ActivityStatus.Draft: return 'info';
      case ActivityStatus.Pending: return 'warning';
      case ActivityStatus.Passed: return 'primary';
      case ActivityStatus.Unpassed: return 'danger';
      case ActivityStatus.Registering: return 'primary';
      case ActivityStatus.NotStart: return 'success';
      case ActivityStatus.InProcess: return 'success';
      case ActivityStatus.End: return 'info';
    }
  }

  get text() {
    switch (this.status) {
      case ActivityStatus.Draft: return '草稿';
      case ActivityStatus.Pending: return '等待审核';
      case ActivityStatus.Passed: return '等待报名';
      case ActivityStatus.Unpassed: return '审核不通过';
      case ActivityStatus.Registering: return '正在报名';
      case ActivityStatus.NotStart: return '等待开始';
      case ActivityStatus.InProcess: return '正在进行';
      case ActivityStatus.End: return '已结束';
    }
  }

  get effect() {
    switch (this.status) {
      case ActivityStatus.Draft: return 'light';
      case ActivityStatus.Pending: return 'dark';
      case ActivityStatus.Passed: return 'light';
      case ActivityStatus.Unpassed: return 'dark';
      case ActivityStatus.Registering: return 'dark';
      case ActivityStatus.NotStart: return 'light';
      case ActivityStatus.InProcess: return 'dark';
      case ActivityStatus.End: return 'dark';
    }
  }
}
</script>