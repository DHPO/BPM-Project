<template>
  <div>
    <Video url="http://ivi.bupt.edu.cn/hls/cctv3hd.m3u8"/>
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

<style scoped>
div {
  overflow: auto;
}
</style>
