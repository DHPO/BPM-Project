<template>
  <controller :activityId="id" />
</template>

<script lang="ts">
import {Component, Vue, Prop} from 'vue-property-decorator';
import Controller from '@/components/Controller/Controller.vue';

@Component({
  components: {
    Controller,
  },
})
export default class ControllerView extends Vue {
  @Prop()
  private id!: string;
}
</script>