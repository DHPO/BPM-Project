<template>
  <div id="iframe-wrapper">
    <iframe ref="iframe" :src="url" />
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop, Ref } from 'vue-property-decorator';

@Component
export default class QuestionnaireResult extends Vue {
  @Prop({ required: true, type: String })
  public url!: string;

  public reloadInterval: number = 30000;

  private reloadHandler!: number;

  @Prop({required: true})
  private enableReload: boolean = true;

  @Ref('iframe')
  private iframe: any;

  private reload() {
    if (this.enableReload) {
      this.iframe.src = this.iframe.src;
    }
  }

  private mounted() {
    this.reloadHandler = setInterval(
      this.reload.bind(this),
      this.reloadInterval,
    );
  }

  private unmounted() {
    clearInterval(this.reloadHandler);
  }
}
</script>

<style lang="css">
#iframe-wrapper,
iframe {
  width: 100%;
  height: 98%;
  border: 0px;
  overflow: auto;
}
iframe-wrapper::-webkit-scrollbar { width: 0 !important }
</style>

