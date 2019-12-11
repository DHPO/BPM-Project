<template>
  <video ref="video"></video>
</template>

<script lang="ts">
import { Component, Vue, Ref, Prop } from 'vue-property-decorator';
import Hls from 'hls.js';

@Component({})
export default class Video extends Vue {
  @Ref('video')
  private video!: any;

  @Prop({required: true})
  private url!: string;

  private mounted() {
    const hls = new Hls();
    hls.loadSource(this.url);
    hls.attachMedia(this.video);
    this.video.play();
  }
}
</script>

<style scoped>
video {
  height: 99vh;
  width: 99vw;
}
</style>