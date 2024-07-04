<script>
import {mixin} from "@/mixins";
import CarCreateAndUpdateForm from "@/components/User/CarPage/CarCreateAndUpdateForm.vue";
import {mapGetters} from "vuex";

export default {
  name: 'CarInfForm',
  components: {CarCreateAndUpdateForm},
  mixins: [mixin],
  data() {
    return {
      dialogShow: false,
      carNewData: {},
      carData: {},
      fileUsage: '',
      carState: false,
      fileUsageType: {
        0: '电动车信息入库',
        1: '电动车信息更新',
      },
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  props: [
    'uid'
  ],
  methods: {
    closeDialog() {
      this.dialogShow = false
    },
    dialogOpen(type) {
      this.fileUsage = this.fileUsageType[type]
      this.dialogShow = true
    }
  },
  async created() {
    const res = await this.getCarInformationData(this.token, this.id, this.uid);
    if (res.code === 200) {
      this.carData = res.data
      this.carNewData = Object.assign({}, res.data)
      this.carState = true
    }
  }
}
</script>

<template>
  <div class="container">
    <el-form
        v-if="carState"
        :model="carNewData"
        label-position="top"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item prop="c_PlateNumber">
            <template #label>
              <el-text size="large" tag="b">车牌号</el-text>
            </template>
            <el-text type="info">{{ carNewData.c_PlateNumber }}</el-text>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="u_RealName">
            <template #label>
              <el-text size="large" tag="b">车主</el-text>
            </template>
            <el-text type="info">{{ carNewData.u_RealName }}</el-text>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="c_Brand">
            <template #label>
              <el-text size="large" tag="b">品牌</el-text>
            </template>
            <el-text type="info">{{ carNewData.c_Brand }}</el-text>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="c_Model">
            <template #label>
              <el-text size="large" tag="b">型号</el-text>
            </template>
            <el-text type="info">{{ carNewData.c_Model }}</el-text>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="c_Type">
            <template #label>
              <el-text size="large" tag="b">类型</el-text>
            </template>
            <el-text type="info">{{ carNewData.c_Type }}</el-text>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="c_Color">
            <template #label>
              <el-text size="large" tag="b">颜色</el-text>
            </template>
            <el-text type="info">{{ carNewData.c_Color }}</el-text>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4" @click="dialogOpen(1)">
          <el-form-item>
            <el-button plain type="primary">
              修改
            </el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div v-else
         style="display: flex; flex-direction: column; align-items: center"
    >
      <el-text type="danger">没有查询到您的电动车呢</el-text>
      <el-button type="primary" size="small" @click="dialogOpen(0)"
                 style="margin-top: 10px"
      >
        去登记
      </el-button>
    </div>
  </div>
  <el-dialog
      v-model="dialogShow"
      :title="fileUsage"
      :fullscreen="false"
      :close-on-click-modal="false"
      :destroy-on-close="true"
  >
    <car-create-and-update-form
        :car-data="carNewData"
        :file-usage="fileUsage"
        :uid="uid"
        @handleClose="closeDialog"
    ></car-create-and-update-form>
  </el-dialog>
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
