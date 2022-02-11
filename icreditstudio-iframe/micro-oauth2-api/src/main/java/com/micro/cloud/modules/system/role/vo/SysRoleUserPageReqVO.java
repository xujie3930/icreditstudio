package com.micro.cloud.modules.system.role.vo;

import com.micro.cloud.api.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** @author roy */
@ApiModel("角色分页 Request VO")
public class SysRoleUserPageReqVO extends PageParam {

  @ApiModelProperty(value = "角色id", required = true, example = "212321")
  private String id;

  @ApiModelProperty(value = "用户类型", required = false, example = "用户类型: 1-> 个人用户 2 ->机构用户 3-> 系统用户")
  private Integer type;

  @ApiModelProperty(value = "用户账号", example = "roy", notes = "用户账号模糊查询")
  private String name;

  @ApiModelProperty(value = "用户真实姓名", example = "roy", notes = "用户真实姓名模糊查询")
  private String realName;

  @ApiModelProperty(value = "作废", example = "roy", notes = "作废")
  private String account;

  @ApiModelProperty(value = "组织机构id", example = "roy", notes = "用户模糊查询")
  private String orgId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "SysRoleUserPageReqVO{" +
        "id='" + id + '\'' +
        ", type=" + type +
        ", name='" + name + '\'' +
        ", realName='" + realName + '\'' +
        ", account='" + account + '\'' +
        ", orgId='" + orgId + '\'' +
        '}';
  }
}
