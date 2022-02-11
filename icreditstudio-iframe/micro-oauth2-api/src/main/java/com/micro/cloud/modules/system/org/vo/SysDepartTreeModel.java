package com.micro.cloud.modules.system.org.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

/**
 * 部门表 存储树结构数据的实体类
 *
 * <p>@Author Steve @Since 2019-01-22
 */
public class SysDepartTreeModel extends SysOrgBaseVO {

  private static final long serialVersionUID = 4672897557147461558L;

  @ApiModelProperty(value = "组织机构id", required = true, example = "1234")
  private String id;



  @ApiModelProperty(value = "下级部门节点", required = true, notes = "没有下级部门则为空")
  private List<SysDepartTreeModel> children;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<SysDepartTreeModel> getChildren() {
    return children;
  }

  public void setChildren(List<SysDepartTreeModel> children) {
    this.children = children;
  }

  @Override
  public String toString() {
    return "SysDepartTreeModel{" + "id='" + id + '\'' + ", children=" + children + '}';
  }
}
