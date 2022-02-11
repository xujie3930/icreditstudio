package com.micro.cloud.modules.system.org.vo;

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
public class SysOrgNodeVO implements Serializable {

  private static final long serialVersionUID = 8803983230315705852L;

  @ApiModelProperty(value = "组织机构id", required = false, example = "1234")
  private String key;

  @ApiModelProperty(value = "组织机构名称", required = false, example = "工程建设中心")
  private String title;

  @ApiModelProperty(value = "上级id", required = true, example = "4321")
  private String parentId;

  @ApiModelProperty(value = "是否为叶子结点", example = "true")
  private Boolean isLeaf;

  @ApiModelProperty(value = "启用状态", example = "true")
  private Boolean status;

  @ApiModelProperty(value = "下级部门")
  private List<SysOrgNodeVO> children;

  public List<SysOrgNodeVO> getChildren() {
    return children;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
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

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public void setChildren(List<SysOrgNodeVO> children) {
    this.children = children;
  }

  @Override
  public String toString() {
    return "SysOrgNodeVO{"
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
        + ", status="
        + status
        + ", children="
        + children
        + '}';
  }
}
