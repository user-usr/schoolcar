<script>

import axios from "@/Axios";
import {mapGetters} from "vuex";
import {mixin} from "@/mixins";

export default {
  name: 'InsertWorkerForm',
  mixins: [mixin],
  data() {
    return {
      logonData: {},
      theRules: {
        w_RealName: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
        ],
        w_JobNumber: [
          {required: true, message: '请输入工号', trigger: 'blur'},
        ],
        w_Department: [
          {required: true, message: '请输入部门', trigger: 'blur'},
        ],
        w_Branch: [
          {required: true, message: '请输入科室', trigger: 'blur'},
        ],
        w_Tel: [
          {required: true, message: '请输入电话', trigger: 'blur'},
          {pattern: /^1[3|4|5|6|7|8|9]\d{9}$/, message: '请输入正确的电话号码', trigger: 'blur'}
        ],
        w_Email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
        ],
        w_ID: [
          {required: true, message: '请输入身份证号', trigger: 'blur'},
          {pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '请输入正确的身份证号码', trigger: 'blur'}
        ]
      }
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    logon() {
      this.$refs['ruleFormRef'].validate(async (valid) => {
        if (valid) {
          const res = await axios({
            url: '/worker/insert-worker',
            method: 'post',
            headers: {
              token: this.token,
              id: this.id,
            },
            data: this.logonData
          }).then(res => {
            return res.data
          })
          if (res.code === 200) {
            this.showElNotification('注册成功', '', 'success')
            this.logonData = {}
            this.$emit('createSuccess')
          }
        }
      })
    }
  }
}
</script>

<template>
  <el-form
      ref="ruleFormRef"
      :model="logonData"
      class="logon-form"
      label-position="right"
      :rules="theRules"
  >
    <el-row>
      <el-form-item label="姓名" prop="w_RealName">
        <el-input v-model="logonData.w_RealName"/>
      </el-form-item>
      <el-form-item label="工号" prop="w_JobNumber">
        <el-input v-model="logonData.w_JobNumber"/>
      </el-form-item>
    </el-row>
    <el-row>
      <el-form-item label="部门" prop="w_Department">
        <el-input v-model="logonData.w_Department"/>
      </el-form-item>
      <el-form-item label="科室" prop="w_Branch">
        <el-input v-model="logonData.w_Branch"/>
      </el-form-item>
    </el-row>
    <el-row>
      <el-form-item label="电话" prop="w_Tel">
        <el-input v-model="logonData.w_Tel"/>
      </el-form-item>
      <el-form-item label="邮箱" prop="w_Email">
        <el-input v-model="logonData.w_Email"/>
      </el-form-item>
    </el-row>
    <el-row>
      <el-form-item label="身份证" prop="w_ID">
        <el-input v-model="logonData.w_ID"/>
      </el-form-item>
    </el-row>
    <el-form-item class="el-form-logon">
      <el-button type="primary" @click="logon">注册</el-button>
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
