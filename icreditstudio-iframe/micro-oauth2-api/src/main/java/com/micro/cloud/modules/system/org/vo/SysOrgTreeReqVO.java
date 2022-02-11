package com.micro.cloud.modules.system.org.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/** @author roy */
@ApiModel("组织机构树形查询 Request VO")
public class SysOrgTreeReqVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "上级部门id", example = "2345", notes = "没有上级部门则为空(root节点)")
  private String parentId;

  @ApiModelProperty(value = "部门名称", example = "人力资源部", notes = "模糊匹配")
  private String name;

  @ApiModelProperty(value = "部门编码", example = "234r")
  private String orgCode;

  @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 SysCommonStatusEnum 枚举类")
  private Boolean status;

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "SysOrgTreeReqVO{"
        + "parentId='"
        + parentId
        + '\''
        + ", name='"
        + name
        + '\''
        + ", orgCode='"
        + orgCode
        + '\''
        + ", status="
        + status
        + '}';
  }
}
