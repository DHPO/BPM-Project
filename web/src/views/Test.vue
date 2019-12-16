<template>
  <div>
    <Editor/>
  </div>
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
</style>
