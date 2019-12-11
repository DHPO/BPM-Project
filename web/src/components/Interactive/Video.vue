<template>
  <video ref="video"></video>
</template>

<script lang="ts">

import {Component, Vue, Ref} from 'vue-property-decorator';
import Hls from 'hls.js';

@Component({})
export default class Video extends Vue {
  @Ref('video')
  private video!: any;

  private mounted() {
    const hls = new Hls();
    hls.loadSource('https://up.imgupio.com/demo/birds.m3u8');
    hls.attachMedia(this.video);
    this.video.play();
  }
}

</script>