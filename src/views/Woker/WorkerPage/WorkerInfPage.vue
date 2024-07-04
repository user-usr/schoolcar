<script>
import WorkerInfForm from "@/components/Worker/WorkerInfForm.vue";
import {mapGetters} from "vuex";

export default {
  name: 'WorkerInfPage',
  components: {WorkerInfForm},
  computed: {
    ...mapGetters({
      wid: 'basic/id',
    })
  }

}
</script>

<template>
  <div class="user-container">
    <worker-inf-form :wid="wid"></worker-inf-form>
  </div>
</template>

<style scoped>

</style>
