<script>
import {mixin} from "@/mixins";

export default {
  name: 'LoginPage',
  mixins: [mixin],
  data() {
    return {
      onLogin: false,
      loginData: {
        loginID: '',
        loginPassword: ''
      },
      rules: {
        loginID: [
          {required: true, message: '请输入学号或工号', trigger: 'blur'},
        ],
        loginPassword: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ]
      },
    }
  },
  methods: {
    login() {
      this.$refs['ruleFormRef'].validate((valid) => {
        if (valid) {
          this.onLogin = true
          this.toLogin(this.loginData).then(res => {
            if (res.code === 200) {
              if (res.message === '普通用户') {
                this.$store.commit('basic/setNavMsg', Object.assign([], this.$store.getters['user/navMsg']))
                this.$store.commit('basic/setHeaderDropdownMsg', Object.assign([], this.$store.getters['user/headerDropdownMsg']))
                this.$store.commit('basic/setWorkspaceMenuData', Object.assign([], this.$store.getters['user/userWorkPageMenuData']))
              } else if (res.message === '工作人员') {
                this.$store.commit('basic/setNavMsg', Object.assign([], this.$store.getters['worker/navMsg']))
                this.$store.commit('basic/setHeaderDropdownMsg', Object.assign([], this.$store.getters['worker/headerDropdownMsg']))
                this.$store.commit('basic/setWorkspaceMenuData', Object.assign([], this.$store.getters['worker/workerWorkPageMenuData']))
              } else if (res.message === '管理员') {
                this.$store.commit('basic/setNavMsg', Object.assign([], this.$store.getters['admin/navMsg']))
                this.$store.commit('basic/setHeaderDropdownMsg', Object.assign([], this.$store.getters['admin/headerDropdownMsg']))
              }
              this.$store.commit('basic/setID', res.data)
              this.$store.commit('basic/setToken', res.token)
              this.$store.commit('basic/setLoginState', true)
              this.onLogin = false
              this.showElNotification('登录成功', '', 'success')
              this.changeRouter('NewsPage')
            } else {
              this.onLogin = false
              this.showElNotification('登录失败', '账号或密码错误', 'error')
            }
          })
        }
      })
    },
    logon() {
      this.changeRouter('LogonPage')
    }
  }
}
</script>

<template>
  <div class="login-container">
    <div class="tip">
      <p style="font-weight: 700">提醒</p>
      <p style="font-size: .9rem; margin-top: 10px">默认密码为<br>swust + 学号/工号 后四位</p>
    </div>
    <el-form
        ref="ruleFormRef"
        :model="loginData"
        :rules="rules"
        label-width="60%"
        class="login-form"
        label-position="top"
    >
      <el-form-item label="学号或工号" prop="loginID">
        <el-input v-model="loginData.loginID"/>
      </el-form-item>
      <el-form-item label="密码" prop="loginPassword">
        <el-input
            v-model="loginData.loginPassword"
            type="password"
            @keydown.enter="login"
        >
        </el-input>
      </el-form-item>
      <el-form-item class="login-form-button">
        <el-button type="primary" class="login-button" @click="login" :loading="onLogin">登录</el-button>
        <el-button plain class="register-button" @click="logon">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-image: linear-gradient(-20deg, #dcb0ed 0%, #99c99c 100%);
}

.tip {
  padding: 8px 16px;
  background-color: #66b1ff;
  border-radius: 4px;
  border-left: 5px solid #007cff;
  margin: 20px 0;
}

.login-form {
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.login-form {
  font-weight: 600;
}

.el-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  outline: none;
  transition: border-color 0.3s;
}

.el-input:focus {
  border-color: #409eff;
}

.login-button {
  background-color: #409eff;
  border-color: #409eff;
  color: #fff;
  border-radius: 4px;
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.login-button:hover {
  background-color: #66b1ff;
}

.register-button {
  margin-left: 10px;
  color: #409eff;
  background-color: transparent;
  cursor: pointer;
  font-size: 14px;
}

.register-button:hover {
  text-decoration: underline;
}

.login-form-button /deep/ .el-form-item__content {
  display: flex;
  justify-content: space-around;
}

</style>
