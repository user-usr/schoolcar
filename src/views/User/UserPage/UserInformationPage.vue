<script>
import UserInformationForm from "@/components/User/UserPage/UserInformationForm.vue";
import {mapGetters} from "vuex";

export default {
  name: 'UserInformationPage',
  components: {UserInformationForm},
  computed: {
    ...mapGetters({
      uid: "basic/id",
    })
  }
}
</script>

<template>
  <div>
    <user-information-form :uid="uid"></user-information-form>
  </div>
</template>

<style scoped>

</style>
