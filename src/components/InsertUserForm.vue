<script>

import {Check, Close} from "@element-plus/icons-vue";
import {mapGetters} from "vuex";
import {mixin} from "@/mixins";

export default {
  name: 'InsertUserForm',
  mixins: [mixin],
  data() {
    return {
      logonData: {},
      loginAfterLogon: false,
    }
  },
  methods: {
    logon() {
      this.$refs['ruleFormRef'].validate((valid) => {
        if (valid) {
          this.toLogon(this.logonData).then(res => {
            if (res.code === 200) {
              this.showElNotification('注册成功', '', 'success')
              if (this.id.startsWith('A')) {
                this.logonData = {}
                this.$emit('createSuccess')
              }
              if (this.loginAfterLogon) {
                this.toLogin(res.data.u_StudentID, res.data.u_Password)
              }
            }
          })
        }
      })
    }
  },
  computed: {
    Close() {
      return Close
    },
    Check() {
      return Check
    },
    ...mapGetters({
      collegeOptions: 'basic/collegeOptions',
      userInformationRules: 'user/userInformationRules',
      id: 'basic/id',
    })
  }
}
</script>

<template>
  <el-form
      ref="ruleFormRef"
      :rules="userInformationRules"
      :model="logonData"
      class="logon-form"
      label-position="right"
  >
    <el-row>
      <el-form-item label="姓名" prop="u_RealName">
        <el-input v-model="logonData.u_RealName"/>
      </el-form-item>
      <el-form-item label="学号" prop="u_StudentID">
        <el-input v-model="logonData.u_StudentID"/>
      </el-form-item>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="学院" prop="u_College">
          <el-select
              v-model="logonData.u_College"
              placeholder="请选择学院"
              clearable
              filterable
          >
            <el-option v-for="option in collegeOptions" :key="option.value" :value="option.value"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-form-item label="专业" prop="u_Major">
        <el-input v-model="logonData.u_Major"/>
      </el-form-item>
    </el-row>
    <el-row>
      <el-form-item label="年级" prop="u_Grade">
        <el-input v-model="logonData.u_Grade"/>
      </el-form-item>
      <el-form-item label="班级" prop="u_Class">
        <el-input v-model="logonData.u_Class"/>
      </el-form-item>
    </el-row>
    <el-row>
      <el-form-item label="电话" prop="u_Tel">
        <el-input v-model="logonData.u_Tel"/>
      </el-form-item>
      <el-form-item label="邮箱" prop="u_Email">
        <el-input v-model="logonData.u_Email"/>
      </el-form-item>
    </el-row>
    <el-row>
      <el-form-item label="身份证" prop="u_ID">
        <el-input v-model="logonData.u_ID"/>
      </el-form-item>
    </el-row>
    <el-form-item class="el-form-logon">
      <el-button type="primary" @click="logon">注册</el-button>
    </el-form-item>
    <el-form-item size="small" class="el-form-switch" v-if="!id.startsWith('A')">
      <el-switch
          v-model="loginAfterLogon"
          style="margin-left: 10px; --el-switch-on-color: #a5dee5"
          inline-prompt
          :active-icon="Check"
          :inactive-icon="Close"
      >
      </el-switch>
      <el-text style="margin-left: 5px" type="info" :class="{active: loginAfterLogon}">注册成功自动登录</el-text>
    </el-form-item>
  </el-form>
</template>

<style scoped>
.logon-form {
  padding: 20px;
  border-radius: 10px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.logon-form {
  font-weight: 600;
}

.active {
  color: #a5dee5;
}

.el-form-switch /deep/ .el-form-item__content {
  display: flex;
  justify-content: right;
}

.el-form-logon /deep/ .el-form-item__content {
  display: flex;
  justify-content: center;

  left: -30px;
}
</style>
