<script>
import {mapGetters} from "vuex";
import {mixin} from "@/mixins";

export default {
  name: 'CarPage',
  mixins: [mixin],
  data() {
    return {
      indexValue: 1
    }
  },
  methods: {
    change(name, index) {
      if ([2, 3].indexOf(index) !== -1 && this.carState === false) {
        this.showElNotification('查寻失败', '请先入库电动车', 'error')
      } else {
        this.indexValue = index;
        this.$router.push({name: name});
      }
    }
  },
  computed: {
    ...mapGetters({
      carPageMenuMsg: 'user/carPageMenuMsg',
    })
  },
  created() {
    if (sessionStorage.getItem('indexValue')) {
      this.indexValue = JSON.parse(sessionStorage.getItem('indexValue'))
      sessionStorage.removeItem('indexValue')
    }
  },
  beforeUpdate() {
    sessionStorage.setItem('indexValue', JSON.stringify(this.indexValue))
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
          <el-menu-item v-for="item in carPageMenuMsg" :key="item.index" :index="item.index"
                        @click="change(item.name, item.index)">
            <i :class="item.icon"></i>
            <span :class="{clicked: item.index === indexValue}">{{ item.value }}</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<style src="@/assets/css/ContainerPageCommonCss.css" scoped>


</style>
