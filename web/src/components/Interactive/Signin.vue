<template>
  <div>
    <h1>扫码签到</h1>
    <VueQr id="qrcode" :text="url" :size="300" />
    <p>请大家扫描二维码签到</p>
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
  @Prop(String)
  private url!: string;
}
</script>

<style scoped>
</style>