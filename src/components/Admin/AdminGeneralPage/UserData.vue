<script>
import {mapGetters} from "vuex";
import MyStatisticCard from "@/components/Admin/MyStatisticCard.vue";
import EchartsPie from "@/components/Admin/Echarts/EchartsPie.vue"
import axios from "@/Axios";
import EchartsBar from "@/components/Admin/Echarts/EchartsBar.vue";

export default {
  name: 'UserData',
  components: {EchartsBar, MyStatisticCard, EchartsPie},
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  data() {
    return {
      theUserStructure: {
        title: '',
        sumNum: 0,
        data: [],
      },
      theUserCarStructure: {
        title: '',
        sumNum: 0,
        data: [],
      },
      theCarViolationStructure: {
        title: '',
        sumNum: 0,
        data: [],
      },
      theCarViolationTypeStructure: {
        title: '',
        types: [],
        values: [],
      },
    }
  },
  methods: {
    async getUserStructureData() {
      const res = await axios({
        url: '/user-structure',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        }
      }).then(res => {
        return res.data
      })
      if (res.code === 200) {
        this.theUserStructure.title = res.data.title
        this.theUserStructure.data = res.data.structure
        this.theUserStructure.sumNum = res.data.sumNum
      }
    },
    async getUserCarStructureData() {
      const res = await axios({
        url: '/user-car-structure',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        }
      }).then(res => {
        return res.data
      })
      if (res.code === 200) {
        this.theUserCarStructure.title = res.data.title
        this.theUserCarStructure.data = res.data.structure
        this.theUserCarStructure.sumNum = res.data.sumNum
      }
    },
    async getCarViolationStructureData() {
      const res = await axios({
        url: '/car-violation-structure',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        }
      }).then(res => {
        return res.data
      })
      if (res.code === 200) {
        this.theCarViolationStructure.title = res.data.title
        this.theCarViolationStructure.data = res.data.structure
        this.theCarViolationStructure.sumNum = res.data.sumNum
      }
    },
    async getCarViolationTypeStructureData() {
      const res = await axios({
        url: '/car-violation-type-structure',
        method: 'get',
        headers: {
          token: this.token,
          id: this.id,
        }
      }).then(res => {
        return res.data
      })
      if (res.code === 200) {
        this.theCarViolationTypeStructure.title = res.data.title
        this.theCarViolationTypeStructure.types = res.data.types
        this.theCarViolationTypeStructure.values = res.data.values
      }
    },
    initialStyle() {
      const container = document.getElementById("container")
      const echarts = document.getElementsByName('i-echarts')
      const width = container.offsetWidth
      for (const chart of echarts) {
        chart.style.width = width / 3 - 10 + 'px'
        chart.style.height = '300px'
      }
    },
  },
  watch: {
    theUserStructure: {
      handler(newValue) {
        this.$refs['user-structure-echarts'].createEcharts(newValue)
      },
      deep: true
    },
    theUserCarStructure: {
      handler(newValue) {
        this.$refs['user-car-structure-echarts'].createEcharts(newValue)
      },
      deep: true
    },
    theCarViolationStructure: {
      handler(newValue) {
        this.$refs['car-violation-structure-echarts'].createEcharts(newValue)
      },
      deep: true
    },
    theCarViolationTypeStructure: {
      handler(newValue) {
        this.$refs['car-violation-type-structure-echarts'].createEcharts(newValue)
      },
      deep: true
    },
  },
  mounted() {
    this.initialStyle()
  },
  async created() {
    await this.getUserStructureData()
    await this.getUserCarStructureData()
    await this.getCarViolationStructureData()
    await this.getCarViolationTypeStructureData()
  },
}
</script>

<template>
  <div id="container">
    <el-row
        style="display: flex; flex-direction: row; justify-content: space-between; background-color: rgba(102,177,255,0.5); border-radius: 10px">
      <my-statistic-card :number="theUserStructure.sumNum" :title="'用户数量总计'"
                         :tooltip-content="'所有普通用户、工作人员和管理员'"/>
      <el-divider direction="vertical"/>
      <my-statistic-card :number="theUserCarStructure.sumNum" :title="'电动车数量总计'"
                         :tooltip-content="'系统内所有电动车的数量'"/>
      <el-divider direction="vertical"/>
      <my-statistic-card :number="theCarViolationStructure.sumNum" :title="'电动车违规数量总计'"
                         :tooltip-content="'系统内所有电动车的违规数量'"/>
    </el-row>
    <div class="echarts-all-div">
      <echarts-pie ref="user-structure-echarts" name="i-echarts" class="i-echarts" :index-value="0"/>
      <echarts-pie ref="user-car-structure-echarts" name="i-echarts" class="i-echarts" :index-value="1"/>
      <echarts-pie ref="car-violation-structure-echarts" name="i-echarts" class="i-echarts" :index-value="2"/>
      <echarts-bar ref="car-violation-type-structure-echarts" name="i-echarts" class="i-echarts" :index-value="3"/>
    </div>
  </div>
</template>

<style scoped>
.el-divider--vertical {
  border-left-color: rgba(78, 77, 119, 0.5);
  border-left-width: 2px;
  margin: 3px 0 3px 0;
  height: unset;
}

.echarts-all-div {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
}
</style>
