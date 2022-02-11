package com.micro.cloud.modules.system.org.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * @author EDZ
 * @since 2021-11-05
 */
@TableName("sys_org")
@ApiModel(value = "SysOrg对象", description = "")
public class SysOrg extends BaseDO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("组织机构id")
  @TableId("sys_org_id")
  private String sysOrgId;

  @ApiModelProperty("组织机构名称")
  @TableField("org_name")
  private String orgName;

  @ApiModelProperty("组织机构信用代码")
  @TableField("org_credit_code")
  private String orgCreditCode;

  @ApiModelProperty("部门编码")
  @TableField("org_code")
  private String orgCode;

  @ApiModelProperty("行政区域id")
  @TableField("direct_id")
  private String directId;

  @ApiModelProperty("联系人")
  private String contact;

  @ApiModelProperty("组织机构类型：1->外部法人机构  2 ->外部非法人机构 3 ->系统组织机构")
  @TableField("type")
  private Integer type;

  @ApiModelProperty("手机号")
  @TableField("phone")
  private String phone;

  @ApiModelProperty("地址")
  @TableField("address")
  private String address;

  @ApiModelProperty("机构描述")
  @TableField("remark")
  private String remark;

  @ApiModelProperty("是否启用: 0->否 1->是")
  @TableField("status")
  private Boolean status;

  @ApiModelProperty("排序下标: 1,2,3,4...")
  @TableField("order_by")
  private Integer orderBy;

  @ApiModelProperty("创建人名称")
  @TableField("creator_name")
  private String creatorName;

  @ApiModelProperty("创建人部门id")
  @TableField("creator_depart_id")
  private String creatorDepartId;

  @ApiModelProperty("创建人部门名称")
  @TableField("creator_depart_name")
  private String creatorDepartName;

  @ApiModelProperty("更新人名称")
  @TableField("updater_name")
  private String updaterName;

  @ApiModelProperty("更新人部门id")
  @TableField("updater_depart_id")
  private String updaterDepartId;

  @ApiModelProperty("更新人部门名称")
  @TableField("updater_depart_name")
  private String updaterDepartName;

  @ApiModelProperty("上级部门")
  @TableField("parent_id")
  private String parentId;

  @ApiModelProperty("是否为叶子节点: 0->否 1->是")
  @TableField("is_leaf")
  private Boolean isLeaf;

  @ApiModelProperty("组织机构简称")
  @TableField("short_name")
  private String shortName;

  public String getSysOrgId() {
    return this.sysOrgId;
  }

  public void setSysOrgId(String sysOrgId) {
    this.sysOrgId = sysOrgId;
  }

  public String getOrgName() {
    return this.orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getOrgCreditCode() {
    return this.orgCreditCode;
  }

  public void setOrgCreditCode(String orgCreditCode) {
    this.orgCreditCode = orgCreditCode;
  }

  public String getDirectId() {
    return this.directId;
  }

  public void setDirectId(String directId) {
    this.directId = directId;
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

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public Boolean getIsLeaf() {
    return isLeaf;
  }

  public void setIsLeaf(Boolean isLeaf) {
    this.isLeaf = isLeaf;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Boolean getStatus() {
    return this.status;
  }

  public String getCreatorName() {
    return this.creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public String getCreatorDepartId() {
    return this.creatorDepartId;
  }

  public void setCreatorDepartId(String creatorDepartId) {
    this.creatorDepartId = creatorDepartId;
  }

  public String getCreatorDepartName() {
    return this.creatorDepartName;
  }

  public void setCreatorDepartName(String creatorDepartName) {
    this.creatorDepartName = creatorDepartName;
  }

  public String getUpdaterName() {
    return this.updaterName;
  }

  public void setUpdaterName(String updaterName) {
    this.updaterName = updaterName;
  }

  public String getUpdaterDepartId() {
    return this.updaterDepartId;
  }

  public void setUpdaterDepartId(String updaterDepartId) {
    this.updaterDepartId = updaterDepartId;
  }

  public String getUpdaterDepartName() {
    return this.updaterDepartName;
  }

  public void setUpdaterDepartName(String updaterDepartName) {
    this.updaterDepartName = updaterDepartName;
  }

  public String getParentId() {
    return this.parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public Integer getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(Integer orderBy) {
    this.orderBy = orderBy;
  }

  @Override
  public String toString() {
    return "SysOrg{"
        + "sysOrgId='"
        + sysOrgId
        + '\''
        + ", orgName='"
        + orgName
        + '\''
        + ", orgCreditCode='"
        + orgCreditCode
        + '\''
        + ", orgCode='"
        + orgCode
        + '\''
        + ", directId='"
        + directId
        + '\''
        + ", contact='"
        + contact
        + '\''
        + ", type="
        + type
        + ", phone='"
        + phone
        + '\''
        + ", address='"
        + address
        + '\''
        + ", remark='"
        + remark
        + '\''
        + ", status="
        + status
        + ", orderBy="
        + orderBy
        + ", creatorName='"
        + creatorName
        + '\''
        + ", creatorDepartId='"
        + creatorDepartId
        + '\''
        + ", creatorDepartName='"
        + creatorDepartName
        + '\''
        + ", updaterName='"
        + updaterName
        + '\''
        + ", updaterDepartId='"
        + updaterDepartId
        + '\''
        + ", updaterDepartName='"
        + updaterDepartName
        + '\''
        + ", parentId='"
        + parentId
        + '\''
        + ", isLeaf="
        + isLeaf
        + ", shortName='"
        + shortName
        + '\''
        + '}';
  }
}
