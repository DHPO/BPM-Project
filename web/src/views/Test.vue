<template>
  <div>
    <Questionnaire :questionnaireId="51870718"/>
  </div>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import Questionnaire from '@/components/Questionnaire/Questionnaire.vue';

@Component({
  components: {
    Questionnaire,
  },
})
export default class TestView extends Vue {

}
</script>
