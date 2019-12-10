<template>
  <div>
    <Signin v-if="showSignin" :bus="bus" :id="id" :url="interactiveConfig.config.url"/>
    <Questionnaire v-if="showQuestionnaire" :bus="bus" :id="id" :questionnaireId="interactiveConfig.config.id"/>
    <Slide v-if="showSlide" :bus="bus" :id="id" :config="interactiveConfig.config"/>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Prop} from 'vue-property-decorator';
import {InteractiveConfig, InteractiveType} from '@/types/interactive';
import Questionnaire from '@/components/Questionnaire/Questionnaire.vue';
import Signin from './Signin.vue';
import Slide from './Slide.vue';

@Component({
  components: {
    Questionnaire,
    Signin,
    Slide,
  },
})
export default class InteractiveWrapper<T> extends Vue {
  @Prop({required: true})
  private interactiveConfig!: InteractiveConfig<T>;

  @Prop({required: true})
  private bus!: Vue;

  get id() {
    return this.interactiveConfig.id;
  }

  get showSignin() {
    return this.interactiveConfig.type === InteractiveType.Signin;
  }

  get showQuestionnaire() {
    return this.interactiveConfig.type === InteractiveType.Questionnaire;
  }

  get showSlide() {
    return this.interactiveConfig.type === InteractiveType.Slide;
  }
}

</script>