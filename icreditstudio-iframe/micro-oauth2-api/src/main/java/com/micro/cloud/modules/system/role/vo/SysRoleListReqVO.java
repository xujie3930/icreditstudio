package com.micro.cloud.modules.system.role.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/** @author roy */
@ApiModel("组织机构列表查询 Request VO")
public class SysRoleListReqVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "部门名称", example = "人力资源部", notes = "模糊匹配")
  private String name;

  @ApiModelProperty(value = "部门编码", example = "234r")
  private String orgCode;

  @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 SysCommonStatusEnum 枚举类")
  private Boolean status;

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
    return "SysOrgListReqVO{"
        + "name='"
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
