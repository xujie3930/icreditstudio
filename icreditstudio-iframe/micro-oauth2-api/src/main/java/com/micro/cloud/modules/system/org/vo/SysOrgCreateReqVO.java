package com.micro.cloud.modules.system.org.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

/** @author roy */
@ApiModel("创建组织机构 Request VO")
public class SysOrgCreateReqVO extends SysOrgBaseVO {

  @ApiModelProperty(value = "上级部门ID", required = false, example = "23456", notes = "没有上级部门则为空")
  private String parentId;

  @ApiModelProperty(value = "行政区划id", required = false, example = "22222", notes = "选填")
  private String directId;

  @ApiModelProperty(value = "联系人", example = "王七七")
  @Length(max = 20, message = "联系人字符长度不得超过20")
  private String contact;

  @ApiModelProperty(value = "组织机构/部门简称", example = "王七七")
  @Length(max = 20, message = "组织机构/部门简称长度不得超过20")
  private String shortName;

  @ApiModelProperty(value = "组织机构/部门编码", example = "0212")
  @Length(max = 20, message = "组织机构/部门编码长度不得超过20")
  private String orgCode;

  @ApiModelProperty(value = "地址", example = "xxx省xxx市xxx区")
  @Length(max = 256, message = "地址字符长度不得大于200")
  private String address;

  @ApiModelProperty(value = "描述信息", example = "小星星小星星小星星小星星小星星小星星小星星小星星")
  @Length(max = 256, message = "描述信息字符长度不得大于200")
  private String remark;

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getDirectId() {
    return directId;
  }

  public void setDirectId(String directId) {
    this.directId = directId;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "SysOrgCreateReqVO{"
        + "parentId='"
        + parentId
        + '\''
        + ", directId='"
        + directId
        + '\''
        + ", contact='"
        + contact
        + '\''
        + ", shortName='"
        + shortName
        + '\''
        + ", orgCode='"
        + orgCode
        + '\''
        + ", address='"
        + address
        + '\''
        + ", remark='"
        + remark
        + '\''
        + '}';
  }
}
