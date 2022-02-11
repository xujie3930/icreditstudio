package com.micro.cloud.modules.system.resource.vo;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/** 部门表 存储树结构数据的实体类 @Author roy */
public class SysResourceTreeModel extends SysResourceBaseVO {

  @ApiModelProperty(value = "角色id", required = false, example = "1234", notes = "默认获取所有菜单/操作权限")
  private String id;

  @ApiModelProperty(value = "是否为叶子节点", required = false, example = "0", notes = "0 -> 否 1 -> 是")
  private Integer isLeaf;

  @ApiModelProperty(
      value = "当前角色是否拥有此菜单/操作权限",
      required = false,
      example = "0",
      notes = "0 -> 否 1 -> 是")
  private Boolean checked;

  @ApiModelProperty(value = "下级部门节点", required = true, notes = "没有下级部门则为空")
  private List<SysResourceTreeModel> children;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getIsLeaf() {
    return isLeaf;
  }

  public void setIsLeaf(Integer isLeaf) {
    this.isLeaf = isLeaf;
  }

  public Boolean getChecked() {
    return checked;
  }

  public void setChecked(Boolean checked) {
    this.checked = checked;
  }

  public List<SysResourceTreeModel> getChildren() {
    return children;
  }

  public void setChildren(List<SysResourceTreeModel> children) {
    if (children == null) {
      this.isLeaf = 1;
    }
    this.children = children;
  }

  @Override
  public String toString() {
    return "SysResourceTreeModel{"
        + "id='"
        + id
        + '\''
        + ", isLeaf="
        + isLeaf
        + ", checked="
        + checked
        + ", children="
        + children
        + '}';
  }
}
