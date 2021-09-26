import { Graph } from '@antv/x6'

const customLineStyle = { line: { stroke: '#d6e4ff' } }

export const x6Json = {
  // 节点
  nodes: [
    {
      id: 'node1', // String，可选，节点的唯一标识
      x: 350, // Number，必选，节点位置的 x 值
      y: 40, // Number，必选，节点位置的 y 值
      width: 250, // Number，可选，节点大小的 width 值
      height: 34, // Number，可选，节点大小的 height 值
      label: '数据开发名称xxxxx' // String，节点标签
    },
    {
      id: 'node2', // String，节点的唯一标识
      x: 350, // Number，必选，节点位置的 x 值
      y: 120, // Number，必选，节点位置的 y 值
      width: 250, // Number，可选，节点大小的 width 值
      height: 34, // Number，可选，节点大小的 height 值
      label: '数据开发名称xxxxx' // String，节点标签
    },
    {
      id: 'node3', // String，节点的唯一标识
      x: 50, // Number，必选，节点位置的 x 值
      y: 250, // Number，必选，节点位置的 y 值
      width: 250, // Number，可选，节点大小的 width 值
      height: 34, // Number，可选，节点大小的 height 值
      label: '数据开发名称xxxxx' // String，节点标签
    },
    {
      id: 'node4', // String，节点的唯一标识
      x: 350, // Number，必选，节点位置的 x 值
      y: 250, // Number，必选，节点位置的 y 值
      width: 250, // Number，可选，节点大小的 width 值
      height: 34, // Number，可选，节点大小的 height 值
      label: '数据开发名称xxxxx' // String，节点标签
    },
    {
      id: 'node5', // String，节点的唯一标识
      x: 650, // Number，必选，节点位置的 x 值
      y: 250, // Number，必选，节点位置的 y 值
      width: 250, // Number，可选，节点大小的 width 值
      height: 34, // Number，可选，节点大小的 height 值
      label: '数据开发名称xxxxx' // String，节点标签
    },
    {
      id: 'node6', // String，节点的唯一标识
      x: 350, // Number，必选，节点位置的 x 值
      y: 350, // Number，必选，节点位置的 y 值
      width: 250, // Number，可选，节点大小的 width 值
      height: 34, // Number，可选，节点大小的 height 值
      label: '数据开发名称xxxxx' // String，节点标签
    },
    {
      id: 'node7', // String，节点的唯一标识
      x: 350, // Number，必选，节点位置的 x 值
      y: 450, // Number，必选，节点位置的 y 值
      width: 250, // Number，可选，节点大小的 width 值
      height: 34, // Number，可选，节点大小的 height 值
      label: '数据开发名称xxxxx' // String，节点标签
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
  return new Graph({
    container: document.getElementById(id),
    width: 1000,
    height: 600,
    grid: { size: 10, visible: true }
  })
}
