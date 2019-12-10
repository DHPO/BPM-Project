import Vue from 'vue';

declare module 'vue/types/vue' {
  interface Vue {
    $start: any;
  }
}