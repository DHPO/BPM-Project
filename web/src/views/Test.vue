<template>
  <el-card id="card">
    <Editor/>
  </el-card>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import Editor from '@/components/ActivityEditor/editor.vue';

@Component({
  components: {
    Editor,
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
</style>
