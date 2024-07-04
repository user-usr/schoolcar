<script>
import {mixin} from "@/mixins";
import {mapGetters} from "vuex";
import axios from "@/Axios";

export default {
  name: "UserInformationForm",
  mixins: [mixin],
  data() {
    return {
      changing: false,
      userNewData: {},
      userData: {},
    }
  },
  props: [
    'uid'
  ],
  computed: {
    ...mapGetters({
      collegeOptions: 'basic/collegeOptions',
      userInformationRules: 'user/userInformationRules',
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    getUInformationByUID(token, id) {
      return axios({
        url: `/user/getInformation/${this.uid}`,
        method: 'get',
        headers: {
          token: token,
          id: id,
        }
      }).then(res => {
        return res.data
      })
    },
    changeState(type) {
      if (type === 2) {
        this.showElNotification('取消修改', '', 'success')
        this.userNewData = Object.assign({}, this.userData)
      }
      this.changing = !this.changing
    },
    submit(type) {
      this.$refs['userInformationFormRef'].validate((valid) => {
        if (valid) {
          this.updateUserInformation(this.token, this.id, this.userNewData).then(res => {
            if (res.code === 200) {
              this.userData = res.data
              this.showElNotification('修改成功', '', 'success')
            } else {
              this.showElNotification('修改失败', '', 'error')
            }
            this.userNewData = Object.assign({}, this.userData)
            this.changeState(type)
          })
        } else {
          return false
        }
      })
    },
  },
  async created() {
    const res = await this.getUInformationByUID(this.token, this.id);
    if (res.code === 200) {
      this.userData = res.data
      this.userNewData = Object.assign({}, res.data)
    }
  }
}
</script>

<template>
  <div class="user-container">
    <el-form
        ref="userInformationFormRef"
        :model="userNewData"
        label-position="top"
        :rules="userInformationRules"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item prop="u_Name">
            <template #label>
              <el-text size="large" tag="b">用户名</el-text>
            </template>
            <el-text type="info" v-if="!changing">
              {{ userNewData.u_Name }}
            </el-text>
            <el-input v-else v-model="userNewData.u_Name"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="u_RealName">
            <template #label>
              <el-text size="large" tag="b">姓名</el-text>
            </template>
            <el-text type="info" v-if="!changing">
              {{ userNewData.u_RealName }}
            </el-text>
            <el-input v-else v-model="userNewData.u_RealName"/>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item prop="u_StudentID">
            <template #label>
              <el-text size="large" tag="b">学号</el-text>
            </template>
            <el-text type="info" v-if="!changing">
              {{ userNewData.u_StudentID }}
            </el-text>
            <el-input v-else v-model="userNewData.u_StudentID"/>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item prop="u_ID">
            <template #label>
              <el-text size="large" tag="b">身份证</el-text>
            </template>
            <el-text type="info" v-if="!changing">
              {{ userNewData.u_ID }}
            </el-text>
            <el-input v-else v-model="userNewData.u_ID"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="u_College">
            <template #label>
              <el-text size="large" tag="b">学院</el-text>
            </template>
            <el-text type="info" v-if="!changing">
              {{ userNewData.u_College }}
            </el-text>
            <el-select
                v-else
                v-model="userNewData.u_College"
                placeholder="请选择学院"
                clearable
                filterable
            >
              <el-option v-for="item in collegeOptions" :key="item.value" :value="item.value"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="u_Major">
            <template #label>
              <el-text size="large" tag="b">专业</el-text>
            </template>
            <el-text type="info" v-if="!changing">
              {{ userNewData.u_Major }}
            </el-text>
            <el-input v-else v-model="userNewData.u_Major"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="u_Grade">
            <template #label>
              <el-text size="large" tag="b">年级</el-text>
            </template>
            <el-text type="info" v-if="!changing">
              {{ userNewData.u_Grade }}
            </el-text>
            <el-input v-else v-model="userNewData.u_Grade"/>
          </el-form-item>
        </el-col>
        <el-col :span=12>
          <el-form-item prop="u_Class">
            <template #label>
              <el-text size="large" tag="b">班级</el-text>
            </template>
            <el-text type="info" v-if="!changing">
              {{ userNewData.u_Class }}
            </el-text>
            <el-input v-else v-model="userNewData.u_Class"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="u_Tel">
            <template #label>
              <el-text size="large" tag="b">手机号</el-text>
            </template>
            <el-text type="info" v-if="!changing">
              {{ userNewData.u_Tel }}
            </el-text>
            <el-input v-else v-model="userNewData.u_Tel"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="u_Email">
            <template #label>
              <el-text size="large" tag="b">邮箱</el-text>
            </template>
            <el-text type="info" v-if="!changing">
              {{ userNewData.u_Email }}
            </el-text>
            <el-input v-else v-model="userNewData.u_Email"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" v-if="id.startsWith('A')">
          <el-form-item prop="u_Password">
            <template #label>
              <el-text size="large" tag="b">密码</el-text>
            </template>
            <el-text type="info" v-if="!changing">
              {{ userNewData.u_Password }}
            </el-text>
            <el-input v-else v-model="userNewData.u_Password"/>
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
        <el-col :span="4" v-if="changing" @click="submit(1)">
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
  </div>
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
