<script>
import {mapGetters} from "vuex";
import axios from "@/Axios";

export default {
  name: 'CarEnterOutPage',
  data() {
    return {
      startIndex: 0,
      endIndex: 100,
      theCarEnterOutTableData: [],
      emptyText: '没数据',
      carEnterOutData: [],
      datePick: '',
      thePick: {
        id: '',
        plateNumberPick: '',
        startTimePick: '',
        endTimePick: '',
        wherePick: '',
        directionPick: '',
      },
      plateNumbers: [],
      wheres: [],
      directions: [],
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    getCarEnterOutData() {
      return axios({
        url: `/car/car-enter-out-data`,
        method: 'get',
        params: this.thePick,
        headers: {
          token: this.token,
          id: this.id,
        },
      }).then(res => {
        return res.data;
      })
    },
    async getData() {
      this.startIndex = 0
      this.endIndex = 100
      this.carEnterOutData = []
      this.theCarEnterOutTableData = []
      this.plateNumbers = []
      this.wheres = []
      this.directions = []
      this.thePick.startTimePick = this.datePick[0]
      this.thePick.endTimePick = this.datePick[1]
      const res = await this.getCarEnterOutData();
      if (res.code === 200) {
        this.carEnterOutData = res.data
        this.getCarEnterOutTableData()
      } else {
        this.emptyText = res.message
      }
    },
    getCarEnterOutTableData() {
      if (this.theCarEnterOutTableData.length < this.carEnterOutData.length) {
        let data = []
        if (this.endIndex <= this.carEnterOutData.length) {
          data = this.carEnterOutData.slice(this.startIndex, this.endIndex);
        } else {
          data = this.carEnterOutData.slice(this.startIndex, this.carEnterOutData.length);
        }
        this.theCarEnterOutTableData = this.theCarEnterOutTableData.concat(data);
        for (let item of data) {
          if (!this.plateNumbers.includes(item.c_PlateNumber)) {
            this.plateNumbers.push(item.c_PlateNumber)
          }
          if (!this.wheres.includes(item.e_Where)) {
            this.wheres.push(item.e_Where)
          }
          if (!this.directions.includes(item.e_Direction)) {
            this.directions.push(item.e_Direction)
          }
        }
        this.startIndex += 100
        this.endIndex += 100
      }
    },
    async pick() {
      await this.getData()
    },
    async reSet() {
      this.thePick = {
        id: this.id,
        plateNumberPick: '',
        startTime: '',
        endTime: '',
        wherePick: '',
        directionPick: '',
      };
      await this.getData()
    },
    carEnterOutTableLoading() {
      let dom = document.querySelector('.el-scrollbar__wrap');
      dom.addEventListener('scroll', () => {
        const scrollDistance = dom.scrollHeight - dom.scrollTop - dom.clientHeight;
        if (scrollDistance <= 1) {
          this.getCarEnterOutTableData()
        }
      });
    },
    async handleExportCarEnterOut() {
      const data = await this.exportCarEnterOut()
      const link = document.createElement('a');
      let blob = new Blob([data], {
        type: 'application/msexcel;charset=utf-8'
      });
      link.style.display = 'none'
      link.href = URL.createObjectURL(blob);
      link.setAttribute('download', '电动车出入记录.xlsx');
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    exportCarEnterOut() {
      return axios({
        url: '/car/export-car-enter-out',
        method: 'post',
        headers: {
          token: this.token,
          id: this.id,
        },
        data: this.theCarEnterOutTableData,
        responseType: 'blob',
      }).then(res => {
        return res.data
      })
    },
    showAll() {
      this.theCarEnterOutTableData = this.carEnterOutData
      this.plateNumbers = []
      this.wheres = []
      this.directions = []

      this.theCarEnterOutTableData.forEach(item => {
        if (!this.plateNumbers.includes(item.c_PlateNumber)) {
          this.plateNumbers.push(item.c_PlateNumber)
        }
        if (!this.wheres.includes(item.e_Where)) {
          this.wheres.push(item.e_Where)
        }
        if (!this.directions.includes(item.e_Direction)) {
          this.directions.push(item.e_Direction)
        }
      })
    }
  },
  async created() {
    this.thePick.id = this.id
    await this.getData()
  },
  mounted() {
    this.carEnterOutTableLoading()
  }
}
</script>

<template>
  <div>
    <el-row justify="end" style="margin-bottom: 10px;">
      <el-col :span="12">
        <el-text type="danger" size="small">
          默认显示100条数据，下滑显示更多，查看所有请点击全部
          <br>查询功能请结合全部显示和筛选进行
        </el-text>
      </el-col>
      <el-tooltip
          effect="light"
          placement="bottom"
      >
        <template #content>
          <el-text size="small">
            导出的是表格当前显示的数据
          </el-text>
        </template>
        <el-button plain type="warning" @click="handleExportCarEnterOut">导出</el-button>
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
          v-model="thePick.wherePick"
          placeholder="请选择闸门位置"
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

      <el-select
          v-model="thePick.directionPick"
          placeholder="请选择方向"
          size="small"
          style="width: 300px; margin-left: 10px"
          filterable
          clearable
          @clear="thePick.directionPick = ''"
      >
        <el-option
            v-for="item in directions"
            :key="item"
            :value="item"
        />
      </el-select>
      <el-button size="small" style="margin-left: 10px" @click="pick" plain type="primary">筛选</el-button>
      <el-button size="small" @click="reSet" plain type="warning">重置</el-button>
    </el-row>
    <el-table
        :data="theCarEnterOutTableData"
        :empty-text="emptyText"
        height="600"
    >
      <el-table-column label="车牌号" prop="c_PlateNumber"/>
      <el-table-column label="通过时间" prop="e_Time"/>
      <el-table-column label="闸门位置" prop="e_Where"/>
      <el-table-column label="方向" prop="e_Direction"/>
    </el-table>
  </div>
</template>

<style scoped>

</style>
