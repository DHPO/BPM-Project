<template>
  <screen />
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import Screen from '@/components/Interactive/Interactive.vue';

@Component({
  components: {
    Screen,
  },
})
export default class ScreenView extends Vue {}
</script>