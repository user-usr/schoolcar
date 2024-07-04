<script>
import axios from "@/Axios";
import {mapGetters} from "vuex";
import CarInfForm from "@/components/CarInfForm.vue";
import CarCreateAndUpdateForm from "@/components/User/CarPage/CarCreateAndUpdateForm.vue";

export default {
  name: 'AdminCarInfPage',
  components: {CarCreateAndUpdateForm, CarInfForm},
  data() {
    return {
      startIndex: 0,
      endIndex: 100,
      theCarTableData: [],
      emptyText: '没数据',
      carsData: [],
      creating: false,
      updating: false,
      uid: '',
      thePick: {
        plateNumberPick: '',
        realNamePick: '',
        branchPick: '',
        modelPick: '',
        typePick: '',
        colorPick: '',
      },
      plateNumbers: [],
      realNames: [],
      branches: [],
      models: [],
      types: [],
      colors: [],
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    getCarData() {
      return axios({
        url: '/car/car-inf-all',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        },
        params: this.thePick
      }).then(res => {
        return res.data
      })
    },
    async getData() {
      this.startIndex = 0
      this.endIndex = 100
      this.theCarTableData = []
      this.carsData = []
      this.plateNumbers = []
      this.realNames = []
      this.branches = []
      this.models = []
      this.types = []
      this.colors = []
      const res = await this.getCarData()
      if (res.code === 200) {
        this.carsData = res.data
        this.getCarTableData()
      } else {
        this.emptyText = res.message
      }
    },
    getCarTableData() {
      if (this.theCarTableData.length < this.carsData.length) {
        let data = []
        if (this.endIndex <= this.carsData.length) {
          data = this.carsData.slice(this.startIndex, this.endIndex);
        } else {
          data = this.carsData.slice(this.startIndex, this.carsData.length)
        }
        this.theCarTableData = this.theCarTableData.concat(data);
        for (let item of data) {
          if (!this.plateNumbers.includes(item.c_PlateNumber)) {
            this.plateNumbers.push(item.c_PlateNumber)
          }
          if (!this.realNames.includes(item.u_RealName)) {
            this.realNames.push(item.u_RealName)
          }
          if (!this.branches.includes(item.c_Branch)) {
            this.branches.push(item.c_Branch)
          }
          if (!this.models.includes(item.c_Model)) {
            this.models.push(item.c_Model)
          }
          if (!this.types.includes(item.c_Type)) {
            this.types.push(item.c_Type)
          }
          if (!this.types.includes(item.c_Color)) {
            this.colors.push(item.c_Color)
          }
        }
        this.startIndex += 100
        this.endIndex += 100
      }
    },
    clickRow(rowData) {
      this.uid = rowData.uid
      this.updating = true
    },
    async createSuccess() {
      await this.getData()
      this.creating = false
    },
    closeDialog() {
      this.creating = false
    },
    async pick() {
      await this.getData()
    },
    async reSet() {
      this.thePick = {
        plateNumberPick: '',
        realNamePick: '',
        branchPick: '',
        modelPick: '',
        typePick: '',
        colorPick: '',
      };
      await this.getData()
    },
    carTableLoading() {
      let dom = document.querySelector('.el-scrollbar__wrap');
      dom.addEventListener('scroll', () => {
        const scrollDistance = dom.scrollHeight - dom.scrollTop - dom.clientHeight;
        if (scrollDistance <= 1) {
          this.getCarTableData()
        }
      });
    },
    exportCar() {
      return axios({
        url: '/car/export-car-inf',
        method: 'post',
        headers: {
          token: this.token,
          id: this.id,
        },
        data: this.theCarTableData,
        responseType: 'blob',
      }).then(res => {
        return res.data
      })
    },
    async handleExportCar() {
      const data = await this.exportCar()
      const link = document.createElement('a');
      let blob = new Blob([data], {
        type: 'application/msexcel;charset=utf-8'
      });
      link.style.display = 'none'
      link.href = URL.createObjectURL(blob);
      link.setAttribute('download', '电动车信息.xlsx');
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    showAll() {
      this.theCarTableData = this.carsData
      this.carsData = []
      this.plateNumbers = []
      this.realNames = []
      this.branches = []
      this.models = []
      this.types = []
      this.colors = []

      this.theCarTableData.forEach(item => {
        if (!this.plateNumbers.includes(item.c_PlateNumber)) {
          this.plateNumbers.push(item.c_PlateNumber)
        }
        if (!this.realNames.includes(item.u_RealName)) {
          this.realNames.push(item.u_RealName)
        }
        if (!this.branches.includes(item.c_Branch)) {
          this.branches.push(item.c_Branch)
        }
        if (!this.models.includes(item.c_Model)) {
          this.models.push(item.c_Model)
        }
        if (!this.types.includes(item.c_Type)) {
          this.types.push(item.c_Type)
        }
        if (!this.types.includes(item.c_Color)) {
          this.colors.push(item.c_Color)
        }
      })
    }
  },
  async created() {
    await this.getData()
  },
  mounted() {
    this.carTableLoading()
  }
}
</script>

<template>
  <el-row justify="end" style="margin-bottom: 10px">
    <el-col :span="12">
      <el-text type="danger" size="small">
        默认显示100条数据，下滑显示更多，查看所有请点击全部
        <br>查询功能请结合全部显示和筛选进行
      </el-text>
    </el-col>
    <el-button plain type="primary" @click="creating = true">新建</el-button>
    <el-tooltip
        effect="light"
        placement="bottom"
    >
      <template #content>
        <el-text size="small">
          导出的是表格当前显示的数据
        </el-text>
      </template>
      <el-button plain type="warning" @click="handleExportCar">导出</el-button>
    </el-tooltip>
    <el-tooltip
        effect="light"
        placement="bottom"
    >
      <template #content>
        <el-text size="small">
          数量太多可能会导致页面卡顿，请耐心等待
          <br>点击筛选处重置按钮，恢复默认显示
        </el-text>
      </template>
      <el-button plain type="info" @click="showAll">全部</el-button>
    </el-tooltip>
  </el-row>
  <el-row style="display: flex; flex-wrap: nowrap">
    <el-select
        v-model="thePick.plateNumberPick"
        placeholder="请选择车牌号"
        size="small"
        style="width: 300px;"
        filterable
        clearable
        @clear="thePick.plateNumberPick = ''"
    >
      <el-option
          v-for="item in plateNumbers"
          :key="item"
          :value="item"
      />
    </el-select>

    <el-select
        v-model="thePick.realNamePick"
        placeholder="请选择车主"
        size="small"
        style="width: 300px; margin-left: 10px"
        filterable
        clearable
        @clear="thePick.realNamePick = ''"
    >
      <el-option
          v-for="item in realNames"
          :key="item"
          :value="item"
      />
    </el-select>
    <el-select
        v-model="thePick.branchPick"
        placeholder="请选择品牌"
        size="small"
        style="width: 300px; margin-left: 10px"
        filterable
        clearable
        @clear="thePick.branchPick = ''"
    >
      <el-option
          v-for="item in branches"
          :key="item"
          :value="item"
      />
    </el-select>
    <el-select
        v-model="thePick.modelPick"
        placeholder="请选择型号"
        size="small"
        style="width: 300px; margin-left: 10px"
        filterable
        clearable
        @clear="thePick.modelPick = ''"
    >
      <el-option
          v-for="item in models"
          :key="item"
          :value="item"
      />
    </el-select>
    <el-select
        v-model="thePick.typePick"
        placeholder="请选择类型"
        size="small"
        style="width: 300px; margin-left: 10px"
        filterable
        clearable
        @clear="thePick.typePick = ''"
    >
      <el-option
          v-for="item in types"
          :key="item"
          :value="item"
      />
    </el-select>
    <el-select
        v-model="thePick.colorPick"
        placeholder="请选择颜色"
        size="small"
        style="width: 300px; margin-left: 10px"
        filterable
        clearable
        @clear="thePick.colorPick = ''"
    >
      <el-option
          v-for="item in colors"
          :key="item"
          :value="item"
      />
    </el-select>

    <el-button size="small" style="margin-left: 10px" @click="pick" plain type="primary">筛选</el-button>
    <el-button size="small" @click="reSet" plain type="warning">重置</el-button>
  </el-row>
  <el-table
      :data="theCarTableData"
      @row-click="clickRow"
      :empty-text="emptyText"
      height="600"
  >
    <el-table-column label="车牌号" prop="c_PlateNumber"/>
    <el-table-column label="车主" prop="u_RealName"/>
    <el-table-column label="品牌" prop="c_Brand"/>
    <el-table-column label="型号" prop="c_Model"/>
    <el-table-column label="类型" prop="c_Type"/>
    <el-table-column label="颜色" prop="c_Color"/>
  </el-table>
  <el-dialog
      v-model="updating"
      title="修改信息"
      :close-on-click-modal="false"
      :destroy-on-close="true"
  >
    <car-inf-form :uid="uid"></car-inf-form>
  </el-dialog>
  <el-dialog
      v-model="creating"
      :title="'电动车信息入库'"
      :fullscreen="false"
      :close-on-click-modal="false"
      :destroy-on-close="true"
  >
    <car-create-and-update-form
        :car-data="{}"
        :file-usage="'电动车信息入库'"
        :uid="''"
        @handleClose="closeDialog"
    >
    </car-create-and-update-form>
  </el-dialog>
</template>

<style scoped>

</style>
