<script>
export default {
  name: 'EchartsPie',
  data() {
    return {
      theOption: {
        title: {
          text: '',
          subtext: new Date().toLocaleDateString(),
          left: 'center',
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          bottom: '0'
        },
        series: [
          {
            name: '',
            type: 'pie',
            radius: '30%',
            data: '',
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(248,62,62,0.5)'
              }
            },
          }
        ]
      },
    }
  },
  props: [
    'indexValue',
  ],
  methods: {
    createEcharts(theData) {
      this.theOption.title.text = theData.title
      this.theOption.series[0].data = theData.data
      this.theOption.series[0].name = theData.title

      const echartsEle = document.getElementById('the-echarts' + this.indexValue)
      const Echarts = this.$echarts.init(echartsEle)
      Echarts.setOption(this.theOption, true)
    },
  },
  mounted() {

  },
  async created() {

  }
}
</script>

<template>
  <div style="margin-top: 6px;">
    <div :id="'the-echarts' + indexValue" class="the-echarts" style="height: 100%; width: 100%;"/>
  </div>
</template>

<style scoped>
.the-echarts {
  background-color: rgba(139, 139, 139, 0.5);
  border-radius: 10px;
}
</style>
