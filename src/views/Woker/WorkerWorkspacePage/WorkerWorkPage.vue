<script>

import WorkspaceScroller from "@/components/WorkScroller.vue";
import {mapGetters} from "vuex";

export default {
  name: 'WorkerWorkPage',
  components: {WorkspaceScroller},
  data() {
    return {
      activeName: 'all'
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
        <workspace-scroller :state="item.name"></workspace-scroller>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped>

</style>
