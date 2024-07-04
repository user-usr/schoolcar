<script>
import axios from "@/Axios";
import {mapGetters} from "vuex";
import CreateViolationForm from "@/components/Worker/CreateViolationForm.vue";
import CarViolationDescriptions from "@/components/CarViolationDescriptions.vue";

export default {
  name: 'CarViolationPage',
  components: {
    CarViolationDescriptions,
    CreateViolationForm
  },
  data() {
    return {
      startIndex: 0,
      endIndex: 100,
      theCarViolationTableData: [],
      getting: true,
      emptyText: '没数据',
      carViolationData: [],
      submitting: false,
      detailShow: false,
      detailData: {},
      datePick: [],
      thePick: {
        id: '',
        plateNumberPick: '',
        wRealNamePick: '',
        startTimePick: '',
        endTimePick: '',
        typePick: '',
        wherePick: '',
      },
      plateNumbers: [],
      wRealNames: [],
      types: [],
      wheres: [],
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    getViolationData() {
      return axios({
        url: '/car/car-violation-data',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id
        },
        params: this.thePick
      }).then(res => {
        return res.data
      })
    },
    async getData() {
      this.getting = true
      this.startIndex = 0
      this.endIndex = 100
      this.carViolationData = []
      this.theCarViolationTableData = []
      this.plateNumbers = [];
      this.wRealNames = [];
      this.types = [];
      this.wheres = [];
      this.thePick.startTimePick = this.datePick[0]
      this.thePick.endTimePick = this.datePick[1]
      const res = await this.getViolationData();
      if (res.code === 200) {
        this.carViolationData = res.data
        this.getCarViolationTableData()
      } else {
        this.emptyText = res.message
      }
      this.getting = false
    },
    getCarViolationTableData() {
      if (this.theCarViolationTableData.length < this.carViolationData.length) {
        let data = []
        if (this.endIndex <= this.carViolationData.length) {
          data = this.carViolationData.slice(this.startIndex, this.endIndex);
        } else {
          data = this.carViolationData.slice(this.startIndex, this.carViolationData.length)
        }
        this.theCarViolationTableData = this.theCarViolationTableData.concat(data);
        for (let item of data) {
          if (!this.plateNumbers.includes(item.c_PlateNumber)) {
            this.plateNumbers.push(item.c_PlateNumber)
          }
          if (!this.wRealNames.includes(item.w_RealName)) {
            this.wRealNames.push(item.w_RealName)
          }
          if (!this.types.includes(item.v_Type)) {
            this.types.push(item.v_Type)
          }
          if (!this.wheres.includes(item.v_Where)) {
            this.wheres.push(item.v_Where)
          }
        }
        this.startIndex += 100
        this.endIndex += 100
      }
    },
    async closeDialog() {
      await this.getData()
      this.submitting = false
    },
    rowClick(rowData) {
      this.detailData = Object.assign({}, rowData)
      this.detailData.v_Evidence = Object.assign([], rowData.v_Evidence.split(','))
      this.detailShow = true
    },
    async pick() {
      await this.getData()
    },
    async reSet() {
      this.thePick = {
        id: this.id,
        plateNumberPick: '',
        wRealNamePick: '',
        startTimePick: '',
        endTimePick: '',
        typePick: '',
        wherePick: '',
      };
      await this.getData()
    },
    carViolationTableLoading() {
      let dom = document.querySelector('.el-scrollbar__wrap');
      dom.addEventListener('scroll', () => {
        const scrollDistance = dom.scrollHeight - dom.scrollTop - dom.clientHeight;
        if (scrollDistance <= 1) {
          this.getCarViolationTableData()
        }
      });
    },
    async handleExportCarViolation() {
      const data = await this.exportCarViolation()
      const link = document.createElement('a');
      let blob = new Blob([data], {
        type: 'application/msexcel;charset=utf-8'
      });
      link.style.display = 'none'
      link.href = URL.createObjectURL(blob);
      link.setAttribute('download', '电动车违规记录.xlsx');
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    exportCarViolation() {
      return axios({
        url: '/car/export-car-violation',
        method: 'post',
        headers: {
          token: this.token,
          id: this.id,
        },
        data: this.theCarViolationTableData,
        responseType: 'blob',
      }).then(res => {
        return res.data
      })
    },
    showAll() {
      this.theCarViolationTableData = this.carViolationData
      this.plateNumbers = [];
      this.wRealNames = [];
      this.types = [];
      this.wheres = [];

      this.theCarViolationTableData.forEach(item => {
        if (!this.plateNumbers.includes(item.c_PlateNumber)) {
          this.plateNumbers.push(item.c_PlateNumber)
        }
        if (!this.wRealNames.includes(item.w_RealName)) {
          this.wRealNames.push(item.w_RealName)
        }
        if (!this.types.includes(item.v_Type)) {
          this.types.push(item.v_Type)
        }
        if (!this.wheres.includes(item.v_Where)) {
          this.wheres.push(item.v_Where)
        }
      })
    }
  },
  async created() {
    this.thePick.id = this.id
    await this.getData()
  },
  mounted() {
    this.carViolationTableLoading()
  }
}
</script>

<template>
  <div class="car-violation-table">
    <el-row justify="end" style="margin-bottom: 10px;">
      <el-col :span="12">
        <el-text type="danger" size="small">
          默认显示100条数据，下滑显示更多，查看所有请点击全部
          <br>查询功能请结合全部显示和筛选进行
        </el-text>
      </el-col>
      <el-button  v-if="id.startsWith('W')" plain type="primary" @click="submitting = true" style="margin-bottom: 10px;">新建</el-button>
      <el-tooltip
          effect="light"
          placement="bottom"
      >
        <template #content>
          <el-text size="small">
            导出的是表格当前显示的数据
          </el-text>
        </template>
        <el-button plain type="warning" @click="handleExportCarViolation">导出</el-button>
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
          v-model="thePick.wRealNamePick"
          placeholder="请选择办理人"
          size="small"
          style="width: 300px; margin-left: 10px;"
          filterable
          clearable
          @clear="thePick.wRealNamePick = ''"
      >
        <el-option
            v-for="item in wRealNames"
            :key="item"
            :value="item"
        />
      </el-select>
      <el-date-picker
          size="small"
          v-model="datePick"
          type="datetimerange"
          start-placeholder="开始时间"
          range-separator="至"
          end-placeholder="截止时间"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"
          :editable="false"
          style="width: 300px; margin-left: 10px"
      />
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
          v-model="thePick.wherePick"
          placeholder="请选择地点"
          size="small"
          style="width: 300px; margin-left: 10px"
          filterable
          clearable
          @clear="thePick.wherePick = ''"
      >
        <el-option
            v-for="item in wheres"
            :key="item"
            :value="item"
        />
      </el-select>
      <el-button size="small" style="margin-left: 10px" @click="pick" plain type="primary">筛选</el-button>
      <el-button size="small" @click="reSet" plain type="warning">重置</el-button>
    </el-row>
    <el-skeleton :rows="4" style="margin-top: 10px" v-if="getting" :throttle="500"/>

    <el-table
        :data="theCarViolationTableData"
        @row-click="rowClick"
        height="600"
        :empty-text="emptyText"
    >
      <el-table-column label="车牌号" prop="c_PlateNumber"></el-table-column>
      <el-table-column label="办理人员" prop="w_RealName"></el-table-column>
      <el-table-column label="时间" prop="v_Time"></el-table-column>
      <el-table-column label="类型" prop="v_Type"></el-table-column>
      <el-table-column label="地点" prop="v_Where"></el-table-column>
      <el-table-column label="处罚" prop="v_Punish"></el-table-column>
    </el-table>

    <el-dialog
        v-model="submitting"
        title="新建违规"
        :close-on-click-modal="false"
        :destroy-on-close="true"
    >
      <create-violation-form @closeDialog="closeDialog"/>
    </el-dialog>

    <el-dialog
        v-model="detailShow"
        style="padding: 20px 50px 50px 50px"
    >
      <car-violation-descriptions :the-data="detailData"/>
    </el-dialog>
  </div>
</template>

<style scoped>

</style>
