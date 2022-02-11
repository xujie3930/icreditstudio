package com.micro.cloud.modules.system.resource.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

/**
 * 〈部门节点〉
 *
 * @author roy
 * @create 2021/11/10
 * @since 1.0.0
 */
@ApiModel(value = "部门节点信息")
public class SysResourceNodeVO implements Serializable {

  private static final long serialVersionUID = 8803983230315705852L;

  @ApiModelProperty(value = "菜单/功能权限key", required = false, example = "系统管理")
  private String key;

  @ApiModelProperty(value = "菜单/功能权限名称", required = false, example = "用户管理")
  private String title;

  @ApiModelProperty(value = "上级id", required = true, example = "4321")
  private String parentId;

  @ApiModelProperty(value = "是否为叶子结点", example = "true")
  private Boolean isLeaf;

  @ApiModelProperty(value = "对应资源识别码", example = "true")
  private String code;

  @ApiModelProperty(value = "是否选中", example = "true")
  private Boolean isChecked;

  @ApiModelProperty(value = "下级部门")
  private List<SysResourceNodeVO> children;

  public List<SysResourceNodeVO> getChildren() {
    return children;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getParentId() {
    return parentId;
  }

  public Boolean getIsLeaf() {
    return this.isLeaf;
  }

  public void setIsLeaf(Boolean isLeaf) {
    this.isLeaf = isLeaf;
  }

  public Boolean getLeaf() {
    return isLeaf;
  }

  public void setLeaf(Boolean leaf) {
    isLeaf = leaf;
  }

  public Boolean getChecked() {
    return isChecked;
  }

  public void setChecked(Boolean checked) {
    isChecked = checked;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public void setChildren(List<SysResourceNodeVO> children) {
    this.children = children;
  }

  @Override
  public String toString() {
    return "SysResourceNodeVO{"
        + "key='"
        + key
        + '\''
        + ", title='"
        + title
        + '\''
        + ", parentId='"
        + parentId
        + '\''
        + ", isLeaf="
        + isLeaf
        + ", code='"
        + code
        + '\''
        + ", isChecked="
        + isChecked
        + ", children="
        + children
        + '}';
  }
}
