<template>
<el-col>
  <el-row :span="20" id="wrapper" type="flex" justify="center">
    <el-col :span="20">
      <div style="height:15vh;"></div>
      <el-carousel
        :autoplay="false"
        ref="carousel"
        id="carousel"
        height="70vh"
        trigger="click">
        <el-carousel-item>
          <el-card class="card">
            <p class="question-content">答题开始！</p>
          </el-card>
        </el-carousel-item>
        <el-carousel-item
          v-for="(item, i) in config.questions"
          :key="item.text + i">
          <el-card class="card">
            <h3>{{i+1}} / {{config.questions.length}}</h3>
            <p class="question-content">{{item.text}}</p>
          </el-card>
        </el-carousel-item>
      </el-carousel>
    </el-col>
  </el-row>
</el-col>
</template>

<script lang="ts">

import {Component, Vue, Ref, Prop} from 'vue-property-decorator';
import { SlideConfig, SlideState } from '../../types/interactive';

@Component({})
export default class Slide extends Vue {
  @Ref('carousel')
  private carousel!: any;

  @Prop({required: true})
  private id!: string;

  @Prop({required: true})
  private bus!: Vue;

  @Prop({required: true})
  private config!: SlideConfig;

  private state: SlideState = {
    current: 0,
  };

  private setActiveItem(idx: number) {
    this.carousel.setActiveItem(idx+1);
  }

  private updateState(payload: {id: string, state: SlideState}) {
    if (payload.id === this.id) {
      this.state = {...payload.state};
    }
    this.setActiveItem(this.state.current);
  }

  private mounted() {
    this.bus.$on('updateState', this.updateState.bind(this));
  }

}

</script>

<style scoped>
#wrapper {
  width: 80%;
  height: 80vh;
  margin-left: auto;
  margin-right: auto;
}
#carousel, .card {
  height: 70vh !important;
}
.card {
  display: flex;
  justify-content: center;
  align-items: center
}
.question-content {
  font-size: 40px;
}
</style>