<template>
  <el-tag :effect="effect" :type="type">{{text}}</el-tag>
</template>

<script lang="ts">
import {Component, Vue, Prop} from 'vue-property-decorator';
import { UserStatus } from '../../types/user';

@Component({})
export default class UserStatusTag extends Vue {
  @Prop()
  private status!: UserStatus;

  get text() {
    switch (this.status) {
      case UserStatus.Normal: return '正常';
      case UserStatus.BlackList: return '封禁';
    }
  }

  get type() {
    switch (this.status) {
      case UserStatus.Normal: return '';
      case UserStatus.BlackList: return 'danger';
    }
  }

  get effect() {
    switch (this.status) {
      case UserStatus.Normal: return 'light';
      case UserStatus.BlackList: return 'dark';
    }
  }
}
</script>