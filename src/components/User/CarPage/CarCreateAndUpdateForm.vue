<script>
import FileUpload from "@/components/FileUpload.vue";
import {mapGetters} from "vuex";
import {mixin} from "@/mixins";
import axios from "@/Axios";

export default {
  name: 'CarCreateAndUpdateForm',
  components: {FileUpload},
  mixins: [mixin],
  data() {
    return {
      thisCarData: {},
      show: true,
      studentIds: [],
      url: {
        '电动车信息入库': '/car/car-create',
        '电动车信息更新': '/car/car-update',
      }
    }
  },
  props: [
    'carData',
    'fileUsage',
    'uid',
  ],
  computed: {
    ...mapGetters({
      carRules: 'user/carRules',
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    submitThisCarData() {
      return axios({
        url: this.url[this.fileUsage],
        method: 'post',
        headers: {
          token: this.token,
          id: this.id,
        },
        data: this.thisCarData
      }).then(res => {
        return res.data
      })
    },
    async getStudentIds() {
      const res = await axios({
        url: '/user/student-id-all',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id
        },
      }).then(res => {
        return res.data
      })
      if (res.code === 200) {
        this.studentIds = res.data
      }
    },
    thisCarDataEvidence(evidence) {
      this.thisCarData.c_Evidence = (this.thisCarData.c_Evidence || '') + evidence + ','
    },
    submit() {
      this.$refs['thisRef'].validate(async res => {
        if (res) {
          if (this.thisCarData.c_Evidence !== undefined) {
            if (this.thisCarData.c_Evidence.endsWith(',')) {
              this.thisCarData.c_Evidence = this.thisCarData.c_Evidence.substring(0, this.thisCarData.c_Evidence.length - 1)
            }
          }
          const res = await this.submitThisCarData()
          if (res.code === 200) {
            this.showElNotification('电动车信息已提交', '', 'success')
            this.handleClose()
          } else {
            this.showElNotification(res.message, '', 'error')
          }
        }
      })
    },
    handleClose() {
      this.$emit('handleClose')
    },
    getUserInf() {
      return axios({
        url: `/user/getInformation/${this.uid}`,
        method: 'get',
        headers: {
          token: this.token,
          id: this.id
        },
      }).then(res => {
        return res.data
      })
    }
  },
  async created() {
    this.thisCarData = Object.assign({}, this.carData);
    this.thisCarData.m_Type = this.fileUsage
    if (this.id.startsWith('U')) {
      const res = await this.getUserInf()
      if (res.code === 200) {
        this.thisCarData.u_StudentID = res.data.u_StudentID
      }
    }
    await this.getStudentIds()
  }
}
</script>

<template>
  <el-form
      ref="thisRef"
      :model="thisCarData"
      label-position="top"
      size="small"
      :rules="carRules"
      style="margin: auto"
  >
    <el-form-item label="车牌号" prop="c_PlateNumber">
      <el-input v-model="thisCarData.c_PlateNumber"/>
    </el-form-item>
    <el-form-item v-if="fileUsage === '电动车信息入库' && this.id.startsWith('A')" label="车主学号" prop="u_StudentID">
      <el-select
          v-model="thisCarData.u_StudentID"
          clearable
          placeholder="请选择车主学号"
          filterable
      >
        <el-option
            v-for="item in studentIds" :key="item"
            :value="item"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="品牌" prop="c_Brand">
      <el-input v-model="thisCarData.c_Brand"/>
    </el-form-item>
    <el-form-item label="型号" prop="c_Model">
      <el-input v-model="thisCarData.c_Model"/>
    </el-form-item>
    <el-form-item label="类型" prop="c_Type">
      <el-input v-model="thisCarData.c_Type"/>
    </el-form-item>
    <el-form-item label="颜色" prop="c_Color">
      <el-input v-model="thisCarData.c_Color"/>
    </el-form-item>
    <el-form-item label="凭证" prop="c_Evidence">
      <div style="margin-top: 5px">
        <file-upload :file-usage="fileUsage"
                     @fileSubmit="thisCarDataEvidence"/>
      </div>
    </el-form-item>
    <el-row justify="center">
      <el-form-item class="el-form-item-button">
        <el-button plain type="primary" @click="submit">提交</el-button>
      </el-form-item>
    </el-row>
  </el-form>
</template>

<style scoped>

</style>
