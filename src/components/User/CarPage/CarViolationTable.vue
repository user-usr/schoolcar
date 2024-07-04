<script>

import {mapGetters} from "vuex";
import axios from "@/Axios";

export default {
  name: 'CarViolationTable',
  data() {
    return {
      emptyText: '',
      carViolationData: [],
    }
  },
  computed: {
    ...mapGetters({
      token: 'basic/token',
      id: 'basic/id',
    })
  },
  methods: {
    getCarViolationData(token, id) {
      return axios({
        url: '/car/car-violation-data',
        method: 'get',
        params: {
          uid: id
        },
        headers: {
          token: token,
          id: id
        }
      }).then(res => {
        return res.data
      })
    },
  },
  async created() {
    const res = await this.getCarViolationData(this.token, this.id);
    if (res.code === 200) {
      this.carViolationData = res.data
    } else {
      this.emptyText = res.message
    }
  }
}
</script>

<template>
  <div class="car-violation-table">
    <el-table
        :data="carViolationData"
    >
      <el-table-column label="车牌号" prop="c_PlateNumber"></el-table-column>
      <el-table-column label="办理人员" prop="w_RealName"></el-table-column>
      <el-table-column label="时间" sortable prop="v_Time"></el-table-column>
      <el-table-column label="类型" prop="v_Type"></el-table-column>
      <el-table-column label="地点" prop="v_Where"></el-table-column>
      <el-table-column label="描述" prop="v_Description"></el-table-column>
      <el-table-column label="处罚" prop="v_Punish"></el-table-column>
    </el-table>
  </div>
</template>

<style scoped>

</style>
