<script>

import {mixin} from "@/mixins";
import {mapGetters} from "vuex";

export default {
  name: 'UserChangePasswordPage',
  mixins: [mixin],
  data() {
    return {
      oldPassword: '',
      newPassword: '',
      newPasswordAgain: '',
      updating: false,
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    submit() {
      this.changeUpdating()
      if ([this.oldPassword, this.newPassword, this.newPasswordAgain].includes('')) {
        this.showElNotification('输入不能为空', '', 'error')
        this.changeUpdating()
      } else if (this.newPassword !== this.newPasswordAgain) {
        this.showElNotification('两次输入的密码不一致', '', 'error')
        this.changeUpdating()
      } else {
        this.updateUserPassword(this.token, this.id, this.oldPassword, this.newPassword).then(res => {
          if (res.code === 200) {
            this.oldPassword = '';
            this.newPassword = '';
            this.newPasswordAgain = '';
            this.showElNotification(res.message, '', 'success')
          } else {
            this.showElNotification(res.message, '', 'error')
          }
          this.changeUpdating()
        })
      }
    },
    changeUpdating() {
      this.updating = !this.updating
    }
  },
}
</script>

<template>
  <div class="user-change-password-page-container">
    <el-form
        label-position="top"
        style="margin: auto"
    >
      <el-col>
        <el-form-item label="原密码">
          <el-input v-model="oldPassword"></el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="newPassword"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码">
          <el-input v-model="newPasswordAgain"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="submit" style="margin: auto" :loading="updating" type="primary">提交</el-button>
        </el-form-item>
      </el-col>
    </el-form>
  </div>
</template>

<style scoped>
.user-change-password-page-container {
  display: flex;
  justify-content: left;
  align-items: center;
}

.el-form {
  margin-left: 20px;
}

.el-input {
  width: 500px;
}

.el-form-item {
  font-weight: bold;
}


</style>
