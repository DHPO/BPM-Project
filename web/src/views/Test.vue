<template>
  <div>
    <Video />
  </div>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import Video from '@/components/Interactive/Video.vue';

@Component({
  components: {
    Video,
  },
})
export default class TestView extends Vue {

}
</script>
