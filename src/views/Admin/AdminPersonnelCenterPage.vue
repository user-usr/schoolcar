<script>

import {mapGetters} from "vuex";

export default {
  name: 'AdminPersonnelCenterPage',
  data() {
    return {
      indexValue: 1,
    }
  },
  computed: {
    ...mapGetters({
      adminPersonnelCenterPageMenuMsg: 'admin/adminPersonnelCenterPageMenuMsg'
    })
  },
  methods: {
    clickMenuItem(name, index) {
      this.indexValue = index
      this.$router.push({
        name: name,
      })
    }
  },
  created() {
    if (sessionStorage.getItem('indexValue')) {
      this.indexValue = parseInt(sessionStorage.getItem('indexValue'))
      sessionStorage.removeItem('indexValue')
    }
  },
  beforeUpdate() {
    sessionStorage.setItem('indexValue', this.indexValue.toString())
  }
}
</script>

<template>
  <div class="container-page">
    <el-container>
      <el-aside>
        <el-menu
            :default-active="1"
            :collapse="false"
            active-text-color="#303133"
        >
          <el-menu-item v-for="item in adminPersonnelCenterPageMenuMsg" :key="item.index"
                        :index="item.index" @click="clickMenuItem(item.name, item.index)"
          >
            <i :class="item.icon" style="font-size: large;"/>
            <span :class="{clicked: item.index === indexValue}">
              {{ item.value }}
            </span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<style src="@/assets/css/ContainerPageCommonCss.css" scoped>

</style>
