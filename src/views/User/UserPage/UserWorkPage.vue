<script>
import {mapGetters} from "vuex";
import WorkScroller from "@/components/WorkScroller.vue";
export default {
  name: 'userWorkPage',
  components: {WorkScroller},
  data() {
    return {
      activeName: 'all',
    }
  },
  computed: {
    ...mapGetters({
      workspaceMenuData: 'basic/workspaceMenuData',
    })
  },
  methods: {
  },
  created() {

  }
}
</script>

<template>
  <div class="user-workspace-page">
    <el-tabs
        :model-value="activeName"
        tab-position="top"
    >
      <el-tab-pane v-for="item in workspaceMenuData" :key="item.name"
                   :label="item.label"
                   :name="item.name"
      >
        <work-scroller :state="item.name"></work-scroller>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped>
.user-workspace-page {
  margin-left: 10px;
}
</style>
