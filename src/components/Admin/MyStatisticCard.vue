<script>

import {Warning} from "@element-plus/icons-vue";

export default {
  name: 'MyStatisticCard',
  components: {Warning},
  props: {
    number: {

    },
    title: {

    },
    tooltipContent: {

    }
  }
}
</script>

<template>
  <el-col :span="4" style="max-width: unset">
    <div class="statistic-card">
      <el-statistic :value="number">
        <template #title>
          <div style="display: inline-flex; align-items: center">
            {{title}}
            <el-tooltip
                :content="tooltipContent"
                placement="top"
            >
              <el-icon style="margin-left: 5px" size="12">
                <warning/>
              </el-icon>
            </el-tooltip>
          </div>
        </template>
      </el-statistic>
    </div>
  </el-col>
</template>

<style scoped>
.el-statistic {
  --el-statistic-content-font-size: 28px;
}

.statistic-card {
  margin: 5px;
  height: 100%;
  padding: 0 10px 10px 10px;
  border-radius: 4px;
}
</style>
