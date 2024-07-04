<script>

import CarInfForm from "@/components/CarInfForm.vue";
import {mapGetters} from "vuex";

export default {
  name: 'CarInformationPage',
  components: {CarInfForm},
  computed: {
    ...mapGetters({
      uid: 'basic/id'
    })
  }
}
</script>

<template>
  <div class="container">
    <car-inf-form :uid="uid"></car-inf-form>
  </div>
</template>

<style scoped>

</style>
