<script>
import {mixin} from "@/mixins";


export default {
  name: "NewsListTable",
  mixins: [mixin],
  data() {
    return {}
  },
  props: [
    'expands',
    'thisPage',
    'pageSize',
    'newsData',
  ],
  methods: {
    rowKey(row) {
      return row.nid
    },
    rowClick(row) {
      this.$emit('rowClick', row.nid)
    },
    handleCurrentChange(newPage) {
      this.$emit('pageChange', newPage)
    }
  },
}
</script>

<template>
  <div class="list-table">
    <el-table
        :data="newsData.slice((thisPage - 1) * pageSize, thisPage * pageSize)"
        style="height: auto"
        :row-key="rowKey"
        :expand-row-keys="expands"
        @row-click="rowClick"
    >
      <el-table-column type="expand">
        <template #default="props">
          <div style="background-color: #fdfdfd">
            <el-text class="text-content">
              {{ props.row.n_Content }}
            </el-text>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="标题" prop="n_Title"/>
      <el-table-column label="作者" prop="n_Author"/>
      <el-table-column label="发布时间" prop="n_Time"/>
    </el-table>
    <el-pagination
        small
        layout="prev, pager, next, ->"
        :total="newsData.length"
        :current-page="thisPage"
        :page-size="pageSize"
        @current-change="handleCurrentChange"
        style="text-align: center; justify-content: center"
    >
    </el-pagination>
  </div>
</template>

<style scoped src="@/assets/css/ListTableCss.css">

</style>
