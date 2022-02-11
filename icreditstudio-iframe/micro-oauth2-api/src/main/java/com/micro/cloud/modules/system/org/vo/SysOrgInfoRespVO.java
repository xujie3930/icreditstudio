package com.micro.cloud.modules.system.org.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** @author roy */
@ApiModel("部门信息 Response VO")
public class SysOrgInfoRespVO implements Serializable {

  private static final long serialVersionUID = 6913634126861139441L;

  @ApiModelProperty(value = "部门id", required = true, example = "1024")
  private String id;

  @ApiModelProperty(value = "上级部门id", required = true, example = "1024")
  private String parentId;

  @ApiModelProperty(value = "上级部门名称", required = true, example = "1024")
  private String parentName;

  @ApiModelProperty(value = "组织机构名称", required = true, example = "南京金宁汇科技有限公司")
  private String title;

  @ApiModelProperty(value = "联系电话", required = true, example = "15601691000")
  private String phone;

  @ApiModelProperty(value = "机构/部门编码", required = true, example = "2012222")
  private String orgCode;

  @ApiModelProperty(value = "统一社会信用代码", required = true, example = "2020219827656789")
  private String creditCode;

  @ApiModelProperty(value = "机构/部门简称", required = true, example = "信用中心")
  private String shortName;

  @ApiModelProperty(value = "机构/部门地址", required = true, example = "南京市浦口区xxxx")
  private String address;

  @ApiModelProperty(value = "机构/部门描述", required = true, example = "信用中心是xxxxx")
  private String remark;

  @ApiModelProperty(value = "行政区划", required = true, example = "浦口区江北街道")
  private String directId;

  @ApiModelProperty(value = "联系人", required = true, example = "王xx")
  private String contact;

  @ApiModelProperty(value = "机构/部门类型", required = true, example = "1")
  private Integer orgType;

  @ApiModelProperty(value = "启用状态", required = true, example = "true")
  private Boolean status;

  @ApiModelProperty(value = "排序字段", required = true, example = "true")
  private Integer orderBy;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getCreditCode() {
    return creditCode;
  }

  public void setCreditCode(String creditCode) {
    this.creditCode = creditCode;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
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

  public Integer getOrgType() {
    return orgType;
  }

  public void setOrgType(Integer orgType) {
    this.orgType = orgType;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Integer getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(Integer orderBy) {
    this.orderBy = orderBy;
  }

  @Override
  public String toString() {
    return "SysOrgInfoRespVO{" +
        "id='" + id + '\'' +
        ", parentId='" + parentId + '\'' +
        ", parentName='" + parentName + '\'' +
        ", title='" + title + '\'' +
        ", phone='" + phone + '\'' +
        ", orgCode='" + orgCode + '\'' +
        ", creditCode='" + creditCode + '\'' +
        ", shortName='" + shortName + '\'' +
        ", address='" + address + '\'' +
        ", remark='" + remark + '\'' +
        ", directId='" + directId + '\'' +
        ", contact='" + contact + '\'' +
        ", orgType=" + orgType +
        ", status=" + status +
        ", orderBy=" + orderBy +
        '}';
  }
}
