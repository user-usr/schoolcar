<script>

export default {
  name: 'EchartsBar',
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
        xAxis: {
          type: 'category',
          data: [],
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            type: 'bar',
            data: [],
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
      this.theOption.xAxis.data = theData.types
      this.theOption.series[0].data = theData.values

      const echartsEle = document.getElementById('the-echarts' + this.indexValue)
      const Echarts = this.$echarts.init(echartsEle)
      Echarts.setOption(this.theOption, true)
    },
  },
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
