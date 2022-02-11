package com.micro.cloud.modules.system.role.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * 〈菜单/操作权限绑定信息〉
 *
 * @author roy
 * @create 2021/11/18
 * @since 1.0.0
 */
public class ResourceBindInfoVo implements Serializable {

  private static final long serialVersionUID = -3069895979879510827L;

  @ApiModelProperty(value = "资源/操作权限id", required = true, example = "201882")
  private String key;

  @ApiModelProperty(value = "上级id", required = true, example = "00213")
  private String parentId;

  @ApiModelProperty(value = "是否为叶子节点", required = true, example = "true")
  @JsonProperty("isLeaf")
  private Boolean isLeaf;

  @ApiModelProperty(value = "资源/操作权限名称", required = true, example = "用户管理")
  private String title;

  public ResourceBindInfoVo() {}

  public ResourceBindInfoVo(String key, String parentId, Boolean isLeaf, String title) {
    this.key = key;
    this.parentId = parentId;
    this.isLeaf = isLeaf;
    this.title = title;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public Boolean getLeaf() {
    return isLeaf;
  }

  public void setLeaf(Boolean leaf) {
    isLeaf = leaf;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object other) {
    System.out.println("equals method invoked!");

    if (other == this) {
      return true;
    }
    if (!(other instanceof ResourceBindInfoVo)) {
      return false;
    }
    ResourceBindInfoVo vo = (ResourceBindInfoVo) other;
    return vo.key.equals(key)
        && vo.parentId.equals(parentId)
        && vo.isLeaf.equals(isLeaf)
        && vo.title.equals(title);
  }

  @Override
  public int hashCode() {
    return System.identityHashCode(this);
  }

  @Override
  public String toString() {
    return "ResourceBindInfoVo{"
        + "key='"
        + key
        + '\''
        + ", parentId='"
        + parentId
        + '\''
        + ", isLeaf="
        + isLeaf
        + ", title='"
        + title
        + '\''
        + '}';
  }
}
