<script>

import {mapGetters} from "vuex";
import axios from "@/Axios";
import FileUpload from "@/components/FileUpload.vue";
import {mixin} from "@/mixins";
import {Check, Close} from "@element-plus/icons-vue";

export default {
  name: 'CreateViolationForm',
  components: {FileUpload},
  mixins: [mixin],
  data() {
    return {
      submitting: false,
      sendEmail: true,
      violationData: {
        c_PlateNumber: '',
        v_Time: '',
        v_Type: '',
        v_Where: '',
        v_Punish: '',
        v_Description: '',
      },
      violationFormRules: {
        c_PlateNumber: [
            {required: true, message: '请选择车牌号', trigger: 'blur'}
        ],
        v_Time: [
            {required: true, message: '请选择时间', trigger: 'blur'}
        ],
        v_Type: [
            {required: true, message: '请选择违规类型', trigger: 'blur'}
        ],
        v_Where: [
            {required: true, message: '请输入违规地点', trigger: 'blur'}
        ],
        v_Punish: [
            {required: true, message: '请输入处罚', trigger: 'blur'}
        ],
        v_Description: [
          {required: true, message: '请输入相关描述', trigger: 'blur'}
        ],
        v_Evidence: [
            {required: true, message: '请上传证据', trigger: 'blur'}
        ]
      },
      plateNumbers: [],
      types: [
          '违规停车'
      ],
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
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    async insertViolation() {
      return await axios({
        url: 'car/insert-violation',
        method: 'post',
        headers: {
          token: this.token,
          id: this.id,
        },
        params: {
          sendEmail: this.sendEmail
        },
        data: this.violationData,
      }).then(res => {
        return res.data
      })
    },
    async getPlateNumbers() {
      await axios({
        url: 'car/car-plate-numbers',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        }
      }).then(res => {
        this.plateNumbers = res.data.data
      })
    },
    fileSub(evidence) {
      this.violationData.v_Evidence = (this.violationData.v_Evidence || '') + evidence + ','
    },
    submit() {
      this.submitting = true
      this.$refs['violationFormRef'].validate(res => {
        if (res) {
          if (this.violationData.v_Evidence !== undefined && this.violationData.v_Evidence.endsWith(',')) {
            this.violationData.v_Evidence = this.violationData.v_Evidence.substring(0, this.violationData.v_Evidence.length - 1)
          }
          this.insertViolation().then(res => {
            this.submitting = false
            if (res.code === 200) {
              this.showElNotification('添加成功', '', 'success')
              this.$emit('closeDialog')
            } else {
              this.showElNotification(res.message, '', 'error')
            }
          })
        } else {
          this.submitting = false
        }
      })
    }
  },
  async created() {
    await this.getPlateNumbers()
    this.violationData.wid = this.id
  }
}
</script>

<template>
  <el-form
      ref="violationFormRef"
      :model="violationData"
      :rules="violationFormRules"
      label-position="top"
  >
    <el-row>
      <el-form-item label="车牌号" prop="c_PlateNumber">
        <template #default>
          <el-select
              v-model="violationData.c_PlateNumber"
              placeholder="选择车牌号"
              clearable
              @clear="violationData.c_PlateNumber = ''"
              filterable
          >
            <el-option v-for="item in plateNumbers" :key="item"
                       :value="item"/>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="时间" prop="v_Time">
        <template #default>
          <el-date-picker
              v-model="violationData.v_Time"
              type="datetime"
              placeholder="请选择日期时间"
              clearable
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </template>
      </el-form-item>
      <el-form-item label="类型" prop="v_Type">
        <template #default>
          <el-select
              v-model="violationData.v_Type"
              placeholder="请选择类型"
              clearable
              allow-create
              default-first-option
              @clear="violationData.v_Type = ''"
              filterable
          >
            <el-option v-for="item in types" :key="item"
                       :value="item"/>
          </el-select>
        </template>
      </el-form-item>
    </el-row>
    <el-row>
      <el-form-item label="地点" prop="v_Where">
        <template #default>
          <el-input
              v-model="violationData.v_Where"
              placeholder="请输入地点"
              clearable
          />
        </template>
      </el-form-item>
      <el-form-item label="处罚" prop="v_Punish">
        <template #default>
          <el-input
              v-model="violationData.v_Punish"
              placeholder="请输入处罚"
              clearable
          />
        </template>
      </el-form-item>
    </el-row>
    <el-form-item label="描述" prop="v_Description">
      <template #default>
        <el-input
            v-model="violationData.v_Description"
            placeholder="请描述相关信息"
            type="textarea"
            autosize
        />
      </template>
    </el-form-item>
    <el-form-item label="凭证" prop="v_Evidence">
      <template #default>
        <file-upload file-usage="电动车违规信息上传" @fileSubmit="fileSub"/>
      </template>
    </el-form-item>
    <el-row>
      <el-form-item>
        <template #default>
          <el-button plain type="danger" @click="submit" :loading="submitting">提交</el-button>
          <div>
            <el-switch
                v-model="sendEmail"
                style="margin-left: 10px; --el-switch-on-color: #a5dee5"
                inline-prompt
                :active-icon="Check"
                :inactive-icon="Close"
            >
            </el-switch>
            <el-text style="margin-left: 5px" type="info" :class="{active: sendEmail}">发送邮箱通知</el-text>
          </div>
        </template>
      </el-form-item>
    </el-row>
  </el-form>
</template>

<style scoped>
.el-row {
  display: flex;
  justify-content: space-around;
}

.el-row /deep/ .el-form-item__content {
  min-width: 200px;
}

.active {
  color: #a5dee5;
}
</style>
