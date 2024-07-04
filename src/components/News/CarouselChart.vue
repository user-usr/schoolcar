<script>
import {mixin} from "@/mixins";

export default {
  name: 'CarouselChart',
  mixins: [mixin],
  data() {
    return {
      theData: []
    }
  },
  props: [
    'expands',
    'newsData'
  ],
  computed: {},
  methods: {
    clickNew(nid) {
      this.$emit('clickNew', nid)
    }
  },
  watch: {
    newsData: {
      handler(newVal) {
        if (newVal.length >= 5) {
          this.theData = newVal.slice(0, 5)
        } else {
          this.theData = newVal
        }
      },
      deep: true
    }
  }
}
</script>

<template>
  <div class="carousel-chat">
    <el-carousel
        :autoplay="true"
        trigger="click"
        type="card"
        indicator-position="outside"
        :interval="3000"
    >
      <el-carousel-item v-for="item in theData" :key="item" @click="clickNew(item.nid)">
        <h3 class="">{{ item.n_Title }}</h3>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<style scoped>
.carousel-chat {
  margin: 20px;
}

.el-carousel__item h3 {
  font-size: 14px;
  opacity: 0.75;
  text-align: center;
}

.el-carousel__item {
  background-color: #71c9ce;
  display: flex;
  justify-content: center;
  align-items: center;
}

.el-carousel__item:hover {
  background-color: #a6e3e9;
}

.carousel-chat /deep/ .el-carousel__button {
  background-color: black;
}
</style>
