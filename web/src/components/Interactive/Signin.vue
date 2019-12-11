<template>
  <div>
    <div id="wrapper">
    <div>
      <h1>扫码签到</h1>
      <VueQr id="qrcode" :text="qrcodeText" :size="300" />
      <p>请扫描二维码签到</p>
    </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
// @ts-ignore
import VueQr from 'vue-qr';

@Component({
  components: {
    VueQr,
  },
})
export default class Signin extends Vue {
  @Prop({required: true})
  private activityId!: number;

  get qrcodeText() {
    return JSON.stringify({
      type: 'checkin',
      activityId: this.activityId,
    });
  }
}
</script>

<style scoped>
#wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh !important;
}

h1 {
    font-size: 48px;
  }

p {
  font-size: 20px;
}
</style>
