<script>
import CarouselChart from "@/components/News/CarouselChart.vue";
import NewsListTable from "@/components/News/NewsListTable.vue";
import axios from "@/Axios";
import NotifyListTable from "@/components/News/NotifyListTable.vue";

export default {
  name: 'NewsPage',
  data() {
    return {
      expands: [],
      thisPage: 1,
      pageSize: 5,
      newsData: [],
    }
  },
  components: {
    NotifyListTable,
    CarouselChart,
    NewsListTable,
  },
  methods: {
    getAllNews() {
      return axios({
        url: '/news/getAll',
        method: 'get',
      }).then(res => {
        return res.data
      })
    },
    changeExpands(nid) {
      if (this.expands.includes(nid)) {
        this.expands = []
      } else {
        this.expands = [nid]

        this.scrollToNews(nid)
      }
    },
    scrollToNews(nid) {
      this.pageChange(Math.floor(this.getNidIndex(nid) / this.pageSize) + 1)
      this.$nextTick(() => {
        const element = document.querySelector('.news-list');
        if (!element) return false;
        const rows = document.querySelectorAll(`.el-table__body tr`);
        for (let i = 0; i < rows.length; i++) {
          if (rows[i].__vnode.key === nid) {

            const boundingClientRect = rows[i].getBoundingClientRect();
            const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
            const offsetTop = boundingClientRect.top + scrollTop;

            window.scrollTo({
              top: offsetTop - 100,
              behavior: 'smooth'
            });
            break;
          }
        }
      })
    },
    pageChange(newPage) {
      this.thisPage = newPage
    },
    getNidIndex(nid) {
      for (let i = 0; i < this.newsData.length; i++) {
        if (nid === this.newsData[i].nid) {
          return i;
        }
      }
    },
  },

  async created() {
    const res = await this.getAllNews();
    if (res.code === 200) {
      this.newsData = res.data.sort((a, b) => {
        const dateA = new Date(a.n_Time);
        const dateB = new Date(b.n_Time);
        if (dateA > dateB) {
          return -1;
        } else if (dateA < dateB) {
          return 1;
        } else {
          return 0;
        }
      });
    }
  },
}
</script>

<template>
  <div>
    <CarouselChart :news-data="newsData" :expands="expands" @clickNew="changeExpands"></CarouselChart>
    <el-divider></el-divider>
    <div class="news">
      <el-row :gutter="20">
        <el-col :span="15">
          <div class="news-social" style="margin-left: 10px">
            <el-text>新闻</el-text>
            <NewsListTable :news-data="newsData" :expands="expands" :this-page="thisPage" @pageChange="pageChange"
                           :page-size="pageSize"
                           style="margin-top: 10px"
                           class="news-list"
                           @rowClick="changeExpands"></NewsListTable>
          </div>
        </el-col>
        <el-col :span="9" class="the-table">
          <div class="news-social" style="margin-right: 10px">
            <el-text>通知</el-text>
            <notify-list-table style="margin-top: 10px;"></notify-list-table>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped>
.news-social {
  width: auto;
  padding: 10px; /* 内边距 */
  border: 1px solid #ddd; /* 可选边框 */
  border-radius: 4px; /* 可选圆角 */
  background-color: #f8f9fa; /* 浅灰色背景 */
}

.news-social .el-text { /* 假设el-text是存在的，并需要样式 */
  font-size: 18px; /* 字体大小 */
  color: #333; /* 字体颜色 */
  margin-bottom: 10px; /* 与NewsListTable之间的间距 */
}
</style>
