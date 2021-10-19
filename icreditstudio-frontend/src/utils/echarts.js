/*
 * @Author: lizheng
 * @Description:
 * @Date: 2021-09-24
 */
// echarts 按需引入
import * as echart from 'echarts/core'
import { LineChart, PieChart, BarChart, RadarChart } from 'echarts/charts'
import {
  TitleComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
  ToolboxComponent,
  MarkLineComponent,
  MarkPointComponent,
  DataZoomComponent
} from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'

// 注册必须的组件
echart.use([
  TitleComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
  ToolboxComponent,
  MarkLineComponent,
  MarkPointComponent,
  DataZoomComponent,
  LineChart,
  PieChart,
  BarChart,
  RadarChart,
  CanvasRenderer
])

// 渲染图表
export const renderChart = (id, options) => {
  const chartDom = document.getElementById(id)
  const chartInstance = echart.init(chartDom)
  chartInstance.setOption(options)
  return chartInstance
}

export const defaultHightLight = (chart, index) => {
  // dataIndex属性伟data传入的索引值
  chart.dispatchAction({
    type: 'highlight',
    dataIndex: index
  })

  // 点击生成detip工具条位置
  chart.dispatchAction({
    type: 'showTip',
    seriesIndex: 0,
    position: [120, 220],
    dataIndex: 0
  })

  chart.on('mouseover', e => {
    if (e.dataIndex !== index) {
      // 当鼠标移除的时候 使默认的索引值去除默认选中
      chart.dispatchAction({ type: 'downplay', dataIndex: index })
    }
  })

  chart.on('mouseout', () => {
    // 当鼠标移除的时候 使默认的索引值去除默认选中
    chart.dispatchAction({ type: 'highlight', dataIndex: index })
  })
}

export const chartResize = myChart => myChart.resize()

export const echarts = echart
