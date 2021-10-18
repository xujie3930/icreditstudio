import { echarts } from '@/utils/echarts'

// 当天运行情况 - 饼图
export const runtimeOptions = {
  color: ['#ff7a7b', '#6699ff', '#ffae31', '#52cca3'],
  textStyle: { color: '#fff' },
  tooltip: { trigger: 'item' },
  legend: {
    orient: 'vertical',
    top: '25%',
    right: '18%',
    icon: 'circle',
    itemWidth: 10,
    itemGap: 30,
    color: '#fff',
    textStyle: {
      color: '#333',
      fontSize: 12,
      fontWeight: 400,
      fontFamily: 'PingFang SC, PingFang SC-Regular'
    }
  },
  series: [
    {
      name: '运行情况',
      type: 'pie',
      width: 280,
      height: 280,
      label: { show: false },
      labelLine: { show: false, showAbove: false },
      radius: ['40%', '70%'],
      data: [
        { value: 0, name: '运行失败' },
        { value: 0, name: '运行中' },
        { value: 0, name: '等待中' },
        { value: 0, name: '运行成功' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}

// 调度任务数量情况- 折线图
export const scheduleTaskOptions = {
  tooltip: {
    trigger: 'axis'
  },
  lineStyle: {
    color: '#1890ff'
  },
  areaStyle: {
    // 颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
    color: echarts.graphic.LinearGradient(0, 0, 0, 1, [
      {
        offset: 0,
        color: 'rgba(80,141,255,0.39)'
      },
      {
        offset: 0.34,
        color: 'rgba(56,155,255,0.25)'
      },
      {
        offset: 1,
        color: 'rgba(38,197,254,0.00)'
      }
    ])
  },
  grid: {
    left: '2%',
    right: '6%',
    bottom: '9%',
    containLabel: true
  },

  xAxis: {
    type: 'category',
    name: '2021',
    boundaryGap: false,
    data: ['10/1', '10/2', '10/3', '10/4', '10/5', '10/6', '10/7'],
    axisLabel: {
      formatter: val => val,
      fontWeight: 400,
      fontSize: 12,
      color: '#8c8c8c',
      fontFamily: 'SourceHanSansCN, SourceHanSansCN-Regular'
    }
  },
  yAxis: {
    type: 'value',
    axisLabel: {
      formatter: val => val,
      fontWeight: 400,
      fontSize: 12,
      color: '#8c8c8c',
      fontFamily: 'SourceHanSansCN, SourceHanSansCN-Regular'
    }
  },
  series: [
    {
      name: '浏览次数',
      type: 'line',
      stack: '总量1',
      areaStyle: {},
      data: [
        '82230',
        '120230',
        '110230',
        '120232',
        '130230',
        '70230',
        '100230'
      ],
      itemStyle: { color: '#32A8FF' }
    }
  ]
}

// 渲染容器id与渲染参数options的映射
export const optionsMapping = {
  pieChart: runtimeOptions,
  lineChart: scheduleTaskOptions
}
