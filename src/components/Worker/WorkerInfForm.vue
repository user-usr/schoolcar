<script>
import {mapGetters} from "vuex";
import axios from "@/Axios";
import {mixin} from "@/mixins";

export default {
  name: 'WorkerInfForm',
  mixins: [mixin],
  data() {
    return {
      updating: false,
      changing: false,
      workerData: {},
      workerNewData: {},
      workerInfRules: {
        w_Name: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        w_RealName: [
            {required: true, message: '请输入姓名', trigger: 'blur'},
        ],
        w_JobNumber: [
            {required: true, message: '请输入工号', trigger: 'blur'},
        ],
        w_ID: [
            {required: true, message: '请输入身份证号', trigger: 'blur'},
            {pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '请输入正确的身份证号', trigger: 'blur'}
        ],
        w_Department: [
            {required: true, message: '请输入部门', trigger: 'blur'},
        ],
        w_Branch: [
            {required: true, message: '请输入科室', trigger: 'blur'},
        ],
        w_Tel: [
            {required: true, message: '请输入电话', trigger: 'blur'},
            {pattern: /^1[3456789]\d{9}$/, message: '请输入正确的电话号码', trigger: 'blur'}
        ],
        w_Email: [
            {required: true, message: '请输入邮箱', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
        ],
        w_Password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
        ]
      },
    }
  },
  props: [
      'wid'
  ],
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    getWInfByWID() {
      return axios({
        url: `/worker/getInformation/${this.wid}`,
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        }
      }).then(res => {
        return res.data
      })
    },
    updateWorkerInformation() {
      return axios({
        url: `/worker/update/${this.workerNewData.wid}`,
        method: 'put',
        data: this.workerNewData,
        headers: {
          token: this.token,
          id: this.id,
        }
      }).then(res => {
        return res.data
      })
    },
    changeState(type) {
      if (type === 2) {
        this.showElNotification('取消修改', '', 'success')
        this.workerNewData = Object.assign({}, this.workerData)
      }
      this.changing = !this.changing
    },
    submit(type) {
      this.$refs['workerInfFormRef'].validate((valid) => {
        if (valid) {
          this.updating = true;
          this.updateWorkerInformation().then(res => {
            if (res.code === 200) {
              this.workerData = res.data
              this.showElNotification('修改成功', '', 'success')
            } else {
              this.showElNotification('修改失败', '', 'error')
            }
            this.workerNewData = Object.assign({}, this.workerData)
            this.updating = false;
            this.changeState(type)
          })
        } else {
          this.updating = false;
          return false
        }
      })
    }
  },
  async created() {
    const res = await this.getWInfByWID();
    if (res.code === 200) {
      this.workerData = res.data
      this.workerNewData = Object.assign({}, res.data)
    }
  }
}
</script>

<template>
  <el-form
      ref="workerInfFormRef"
      :model="workerNewData"
      label-position="top"
      :rules="workerInfRules"
  >
    <el-row>
      <el-col :span="6">
        <el-form-item prop="w_Name">
          <template #label>
            <el-text size="large" tag="b">用户名</el-text>
          </template>
          <el-text type="info" v-if="!changing">
            {{ workerNewData.w_Name }}
          </el-text>
          <el-input v-else v-model="workerNewData.w_Name"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item prop="w_RealName">
          <template #label>
            <el-text size="large" tag="b">姓名</el-text>
          </template>
          <el-text type="info" v-if="!changing">
            {{ workerNewData.w_RealName }}
          </el-text>
          <el-input v-else v-model="workerNewData.w_RealName"/>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item prop="w_JobNumber">
          <template #label>
            <el-text size="large" tag="b">工号</el-text>
          </template>
          <el-text type="info" v-if="!changing">
            {{ workerNewData.w_JobNumber }}
          </el-text>
          <el-input v-else v-model="workerNewData.w_JobNumber"/>
        </el-form-item>
      </el-col>
      <el-col :span="7">
        <el-form-item prop="w_ID">
          <template #label>
            <el-text size="large" tag="b">身份证</el-text>
          </template>
          <el-text type="info" v-if="!changing">
            {{ workerNewData.w_ID }}
          </el-text>
          <el-input v-else v-model="workerNewData.w_ID"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item prop="w_Department">
          <template #label>
            <el-text size="large" tag="b">部门</el-text>
          </template>
          <el-text type="info" v-if="!changing">
            {{ workerNewData.w_Department }}
          </el-text>
          <el-input v-else v-model="workerNewData.w_Department"/>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item prop="w_Branch">
          <template #label>
            <el-text size="large" tag="b">科室</el-text>
          </template>
          <el-text type="info" v-if="!changing">
            {{ workerNewData.w_Branch }}
          </el-text>
          <el-input v-else v-model="workerNewData.w_Branch"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item prop="w_Tel">
          <template #label>
            <el-text size="large" tag="b">手机号</el-text>
          </template>
          <el-text type="info" v-if="!changing">
            {{ workerNewData.w_Tel }}
          </el-text>
          <el-input v-else v-model="workerNewData.w_Tel"/>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item prop="w_Email">
          <template #label>
            <el-text size="large" tag="b">邮箱</el-text>
          </template>
          <el-text type="info" v-if="!changing">
            {{ workerNewData.w_Email }}
          </el-text>
          <el-input v-else v-model="workerNewData.w_Email"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12" v-if="id.startsWith('A')">
        <el-form-item prop="w_Password">
          <template #label>
            <el-text size="large" tag="b">密码</el-text>
          </template>
          <el-text type="info" v-if="!changing">
            {{ workerNewData.w_Password }}
          </el-text>
          <el-input v-else v-model="workerNewData.w_Password"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="4" v-if="!changing" @click="changeState(0)">
        <el-form-item>
          <el-button plain type="primary">
            修改
          </el-button>
        </el-form-item>
      </el-col>
      <el-col :span="4" v-if="changing" v-loading="updating" @click="submit(1)">
        <el-form-item>
          <el-button plain type="warning">
            确定
          </el-button>
        </el-form-item>
      </el-col>
      <el-col :span="4" v-if="changing" @click="changeState(2)">
        <el-form-item>
          <el-button plain type="primary">
            取消
          </el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<style scoped>
.el-row {
  display: flex;
  justify-content: space-around;
}

.el-form-item {
  margin-left: 20px;
}
</style>
