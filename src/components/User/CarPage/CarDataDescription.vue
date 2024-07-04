<script>

export default {
  name: 'CarDataDescription',
  data() {
    return {}
  },
  props: [
    'carData',
  ],
  computed: {},
  methods: {},
  created() {

  }
}

</script>

<template>
  <div class="car-data-form" style="background-color: #bfe1ff; border-radius: 5px; padding: 5px; margin: 5px">
    <el-descriptions
        column="1"
        size="small"
        border
    >
      <el-descriptions-item label="车牌号">
        <el-text>{{carData.c_PlateNumber}}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="品牌">
        <el-text>{{carData.c_Brand}}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="型号">
        <el-text>{{carData.c_Model}}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="类型">
        <el-text>{{carData.c_Type}}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="颜色">
        <el-text>{{carData.c_Color}}</el-text>
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<style scoped>

</style>
