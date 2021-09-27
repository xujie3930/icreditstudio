import { Graph } from '@antv/x6'
import '@antv/x6-vue-shape'
import Node from './node.vue'

const customLineStyle = { line: { stroke: '#d6e4ff' } }

export const customNodeStyle = (nodeConfig, cusAttrs) => {
  const defaultAttrs = {
    body: {
      fill: '#f4f4f4', // 背景颜色
      stroke: '#f4f4f4', // 边框颜色
      rx: 4,
      ry: 4
    }
  }
  const attrs = cusAttrs || defaultAttrs
  return {
    ...nodeConfig,
    attrs,
    shape: 'vue-shape',
    component: {
      template: '<Node :node-info="nodeInfo"></Node>',
      components: { Node },
      data() {
        return {
          nodeInfo: {
            bgColor: '#f4f4f4',
            lineColor: '#d6e4ff',
            ...nodeConfig.data
          }
        }
      }
    }
  }
}

export const x6Json = {
  // 节点
  nodes: [
    {
      id: 'node1', // String，可选，节点的唯一标识
      x: 350,
      y: 40,
      width: 250,
      height: 34,
      data: {
        nodeName: '数据开发名称001',
        iconName: 'dag-py',
        status: 'success'
      }
    },
    {
      id: 'node2', // String，节点的唯一标识
      x: 350, // Number，必选，节点位置的 x 值
      y: 120, // Number，必选，节点位置的 y 值
      width: 250, // Number，可选，节点大小的 width 值
      height: 34, // Number，可选，节点大小的 height 值
      data: {
        nodeName: '数据开发名称002',
        iconName: 'dag-sql',
        status: 'error'
      }
    },
    {
      id: 'node3',
      x: 50,
      y: 250,
      width: 250,
      height: 34,
      data: {
        nodeName: '数据开发名称003',
        iconName: 'dag-sh',
        status: 'default'
      }
    },
    {
      id: 'node4', // String，节点的唯一标识
      x: 350, // Number，必选，节点位置的 x 值
      y: 250, // Number，必选，节点位置的 y 值
      width: 250, // Number，可选，节点大小的 width 值
      height: 34, // Number，可选，节点大小的 height 值
      data: {
        nodeName: '数据开发名称004',
        iconName: 'dag-data',
        status: 'default'
      }
    },
    {
      id: 'node5',
      x: 650,
      y: 250,
      width: 250,
      height: 34,
      data: {
        nodeName: '数据开发名称005',
        iconName: 'dag-hql',
        status: 'default'
      }
    },
    {
      id: 'node6', // String，节点的唯一标识
      x: 350, // Number，必选，节点位置的 x 值
      y: 350, // Number，必选，节点位置的 y 值
      width: 250, // Number，可选，节点大小的 width 值
      height: 34, // Number，可选，节点大小的 height 值
      data: {
        nodeName: '数据质量名称xxxxx',
        iconName: 'dag-quality',
        status: 'default'
      }
    },
    {
      id: 'node7', // String，节点的唯一标识
      x: 350, // Number，必选，节点位置的 x 值
      y: 450, // Number，必选，节点位置的 y 值
      width: 250, // Number，可选，节点大小的 width 值
      height: 34, // Number，可选，节点大小的 height 值
      data: {
        nodeName: '数据可视化名称xxxxx',
        iconName: 'dag-view',
        status: 'default'
      }
    }
  ],
  // 边
  edges: [
    {
      source: 'node1', // String，必须，起始节点 id
      target: 'node2', // String，必须，目标节点 id

      attrs: {
        line: {
          stroke: '#52C41A' // 指定 path 元素的填充色
        }
      }
    },
    {
      source: 'node2',
      target: 'node3',
      attrs: { ...customLineStyle },
      vertices: [
        { x: 470, y: 200 },
        { x: 200, y: 200 }
      ],
      router: {
        name: 'orth',
        args: {}
      }
    },
    {
      source: 'node2',
      target: 'node4',
      attrs: { ...customLineStyle }
    },
    {
      source: 'node2',
      target: 'node5',
      attrs: { ...customLineStyle },
      vertices: [
        { x: 470, y: 200 },
        { x: 550, y: 200 }
      ],
      router: {
        name: 'orth',
        args: {}
      }
    },
    { source: 'node4', target: 'node6', attrs: { ...customLineStyle } },
    { source: 'node6', target: 'node7', attrs: { ...customLineStyle } }
  ]
}

// 渲染画布
export const renderGraph = (id = 'container') => {
  const graph = new Graph({
    container: document.getElementById(id),
    width: 1000,
    height: 600,
    selecting: true,
    grid: { size: 10, visible: true }
  })

  // 注册 vue component
  // 如果需要序列化/反序列化数据，必须使用该方式
  Graph.registerVueComponent(
    'node',
    {
      template: '<Node />',
      components: { Node }
    },
    true
  )

  return graph
}
