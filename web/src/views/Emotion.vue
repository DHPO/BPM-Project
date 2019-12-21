<template>
  <el-card id="card">
    <Statistics :activityId="id" />
  </el-card>
</template>

<script lang="ts">
import { Component, Vue, Ref, Prop } from 'vue-property-decorator';
import Statistics from '@/components/statistics/OrganizerActivity.vue';

@Component({
  components: {
    Statistics,
  },
})
export default class TestView extends Vue {
  @Prop()
  private id!: number;
}
</script>

<style scoped>
div {
  overflow: auto;
}
#card {
  width: 60vw;
  margin-left: auto;
  margin-right: auto;
  margin-top: 50px;
}
#shortcut-wrapper {
  width: 60vw;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  align-content: flex-start;
  justify-content: space-evenly;
}
</style>
