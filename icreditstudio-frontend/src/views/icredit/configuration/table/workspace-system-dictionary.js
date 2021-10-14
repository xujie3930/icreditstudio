/*
 * @Author: lizheng
 * @Description: 子节点信息
 * @Date: 2021-10-12
 */

export default {
  id: 'nodeInfo',
  refName: 'nodeInfo',
  isBorder: true,
  hasPage: true,
  group: [
    {
      type: 'index',
      label: '序号',
      width: 80
    },
    {
      type: 'text',
      label: '分类名称',
      prop: 'name'
    },
    {
      type: 'text',
      label: '分类编号',
      prop: 'number'
    },
    {
      type: 'text',
      label: '父级节点',
      prop: 'parent'
    },
    {
      type: 'text',
      label: '根节点',
      prop: 'root'
    },
    {
      type: 'editInput',
      label: '备注',
      prop: 'remark'
    }
  ]
}
