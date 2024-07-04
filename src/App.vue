<template>
  <div id="apps">
    <TheHeader></TheHeader>
    <ScrollTop></ScrollTop>
    <router-view class="content"></router-view>
  </div>
</template>

<script>
import ScrollTop from "@/components/ScrollTop.vue";
import TheHeader from "@/components/TheHeader.vue";

export default {
  name: 'App',
  components: {
    ScrollTop,
    TheHeader
  },
  created() {
    if (sessionStorage.getItem('token')) {
      this.$store.commit('basic/handleRefresh', sessionStorage);
    }

    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem('loginState', JSON.stringify(this.$store.getters['basic/loginState']))
      sessionStorage.setItem('navMsg', JSON.stringify(this.$store.getters['basic/navMsg']))
      sessionStorage.setItem('headerDropdownMsg', JSON.stringify(this.$store.getters['basic/headerDropdownMsg']))
      sessionStorage.setItem('activeName', this.$store.getters['basic/activeName'])
      sessionStorage.setItem('token', this.$store.getters['basic/token'])
      sessionStorage.setItem('id', this.$store.getters['basic/id'])
      sessionStorage.setItem('workspaceMenuData', JSON.stringify(this.$store.getters['basic/workspaceMenuData']))
    })
  },
}
</script>

<style>
#apps {
  display: flex;
  flex-direction: column;

  position: absolute;
  width: 100%;
  height: 100%;
}

* {
  margin: 0;
}

.content {
  flex: 1;
}
</style>
