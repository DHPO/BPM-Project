<template>
  <el-card id="card">
    <Statistics :activityId="1576761135205" />
  </el-card>
</template>

<script lang="ts">
import { Component, Vue, Ref } from 'vue-property-decorator';
import Statistics from '@/components/statistics/OrganizerActivity.vue';

@Component({
  components: {
    Statistics,
  },
})
export default class TestView extends Vue {

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
