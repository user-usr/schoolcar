<script>
import logo from '@/assets/logo.png'
import {ref} from "vue"
import {mapGetters, mapMutations} from "vuex";
import {mixin} from "@/mixins";

const url = ref(logo)
const SchoolCar = ref('SchoolCar')

export default {
  name: "TheHeader",
  mixins: [mixin],
  data() {
    return {
      url: url,
      SchoolCar: SchoolCar,
    }
  },
  computed: {
    ...mapGetters({
      loginState: 'basic/loginState',
      activeName: 'basic/activeName',
      navMsg: 'basic/navMsg',
      loginMsg: 'basic/loginMsg',
      headerDropdownMsg: 'basic/headerDropdownMsg',
    }),
  },
  methods: {
    ...mapMutations({
      setLoginState: 'basic/setLoginState',
      setNavMsg: 'basic/setNavMsg',
      setHeaderDropdownMsg: 'basic/setHeaderDropdownMsg',
    }),
    goPage(name) {
      this.changeRouter(name)
    },

    goHomePage() {
      this.changeRouter('NewsPage')
    },

    handleCommand(command) {
      if (command === 'Logout') {
        this.toLogout()
      } else {
        this.changeRouter(command)
      }
    },
  }
}
</script>

<template>
  <div class="the-header">
    <div class="left">
      <div class="header-logo" @click="goHomePage">
        <div class="logo-img-div">
          <el-image class="logo-img" :src="url" fit="cover">
          </el-image>
        </div>
        <el-text class="logo-name">{{ SchoolCar }}</el-text>
      </div>
      <ul class="navbar">
        <li :class="{active: item.name === activeName}" v-for="item in navMsg" :key="item.name"
            @click="goPage(item.name, item.path)">
          {{ item.value }}
        </li>
      </ul>
    </div>
    <div class="right">
      <ul class="login-f" v-if="!loginState">
        <li :class="{active: item.name === activeName}" v-for="item in loginMsg" :key="item.name"
            @click="goPage(item.name, item.path)">
          {{ item.value }}
        </li>
      </ul>
      <el-dropdown v-else class="login-t" @command="handleCommand">
        <el-avatar :size="40" icon="User" fit="cover" class="user-icon"></el-avatar>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item v-for="item in headerDropdownMsg" :key="item.name" :command="item.name">
              {{ item.value }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>

</template>

<style scoped>
.the-header {
  background-color: #fefefe;
  width: 100%;
  padding: 10px;
  position: relative;
  box-shadow: 0 0 10px black;

  display: flex;
  justify-content: space-between;
}

.left {
  display: flex;
}

.right {
  display: flex;
}

.header-logo {
  position: relative;
  left: 30px;
  cursor: pointer;

  display: flex;
}

.header-logo .logo-img-div {
  margin: auto;
  width: 50px;
  height: 50px;
}

.logo-img-div .logo-img {
  width: 100%;
  height: 100%;
}

.header-logo .logo-name {
  margin-left: 15px;
  font-size: 30px;
  font-weight: bold;
}

.navbar {
  display: flex;
  margin: auto;
}

.navbar li {
  margin-left: 30px;
}

.login-f {
  display: flex;
  margin: auto;
}

.login-f li {
  margin-right: 30px;
}

.login-t {
  display: flex;
  margin: auto;
}

.login-t .user-icon {
  margin-right: 30px;
}

li {
  list-style-type: none;
  cursor: pointer;

  font-size: 30px;
  color: #8b8b8b;
}

.active {
  color: #3366FF;
  text-shadow: 0 8px 10px #6699FF;
}
</style>
