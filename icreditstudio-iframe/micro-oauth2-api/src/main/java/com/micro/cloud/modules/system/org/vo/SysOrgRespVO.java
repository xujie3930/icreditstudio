package com.micro.cloud.modules.system.org.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/** @author roy */
@ApiModel("部门信息 Response VO")
public class SysOrgRespVO implements Serializable {

  private static final long serialVersionUID = 6913634126861139441L;

  @ApiModelProperty(value = "部门id", required = true, example = "1024")
  private String id;

  @ApiModelProperty(value = "部门id(前端适配)", required = true, example = "1024")
  private String key;

  @ApiModelProperty(value = "部门全称", required = true, example = "江苏省信用中心")
  private String title;

  @ApiModelProperty(value = "部门编码", required = true, example = "2012222")
  private String orgCode;

  @ApiModelProperty(value = "部门简称", required = true, example = "信用中心")
  private String shortName;

  @ApiModelProperty(value = "启用状态", required = true, example = "0-> 停用 1 -> 启用")
  private Boolean status;

  @ApiModelProperty(value = "启用状态", required = true, example = "0-> 停用 1 -> 启用")
  private Boolean parentStatus;

  @ApiModelProperty(value = "部门人数", required = true, example = "12")
  private Integer members;

  @ApiModelProperty(value = "排序下标", required = true, example = "1")
  private Integer orderBy;

  @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;

  @ApiModelProperty(value = "联系人", required = true, example = "联系人")
  private String contact;

  @ApiModelProperty(value = "联系方式", required = true, example = "132432412")
  private String phone;

  @ApiModelProperty(value = "组织机构信用代码", required = true, example = "132432412")
  private String creditCode;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKey() {
    return id;
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

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Boolean getParentStatus() {
    return parentStatus;
  }

  public void setParentStatus(Boolean parentStatus) {
    this.parentStatus = parentStatus;
  }

  public Integer getMembers() {
    return members;
  }

  public void setMembers(Integer members) {
    this.members = members;
  }

  public Integer getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(Integer orderBy) {
    this.orderBy = orderBy;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCreditCode() {
    return creditCode;
  }

  public void setCreditCode(String creditCode) {
    this.creditCode = creditCode;
  }

  @Override
  public String toString() {
    return "SysOrgRespVO{" +
            "id='" + id + '\'' +
            ", key='" + key + '\'' +
            ", title='" + title + '\'' +
            ", orgCode='" + orgCode + '\'' +
            ", shortName='" + shortName + '\'' +
            ", status=" + status +
            ", parentStatus=" + parentStatus +
            ", members=" + members +
            ", orderBy=" + orderBy +
            ", createTime=" + createTime +
            ", contact='" + contact + '\'' +
            ", phone='" + phone + '\'' +
            ", creditCode='" + creditCode + '\'' +
            '}';
  }
}
