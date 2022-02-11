package com.micro.cloud.modules.system.role.dataobject;

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
@TableName("sys_role")
@ApiModel(value = "SysRole对象", description = "")
public class SysRole extends BaseDO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("角色id")
  @TableId("sys_role_id")
  private String sysRoleId;

  @ApiModelProperty("角色名称")
  @TableField("role_name")
  private String roleName;

  @ApiModelProperty("角色编码")
  @TableField("role_code")
  private String roleCode;

  @ApiModelProperty("角色信息描述")
  @TableField("remark")
  private String remark;

  @ApiModelProperty("角色数目")
  @TableField("count")
  private Integer count;

  @ApiModelProperty("是否启用: 0->停用 1-> 启用")
  @TableField("status")
  private Boolean status;

  @ApiModelProperty("角色类型: 1->超管 2->数据管理员 3->外部系统用户默认角色 4 ->外部组织机构默认角色")
  @TableField("type")
  private Integer type;

  @ApiModelProperty("角色类别: 1->自定义 2->系统默认角色...")
  @TableField("category")
  private Integer category;

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

  @ApiModelProperty("上级角色id")
  @TableField("parent_role_id")
  private String parentRoleId;

  public String getSysRoleId() {
    return sysRoleId;
  }

  public void setSysRoleId(String sysRoleId) {
    this.sysRoleId = sysRoleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getCategory() {
    return category;
  }

  public void setCategory(Integer category) {
    this.category = category;
  }

  public String getCreatorName() {
    return creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public String getCreatorDepartId() {
    return creatorDepartId;
  }

  public void setCreatorDepartId(String creatorDepartId) {
    this.creatorDepartId = creatorDepartId;
  }

  public String getCreatorDepartName() {
    return creatorDepartName;
  }

  public void setCreatorDepartName(String creatorDepartName) {
    this.creatorDepartName = creatorDepartName;
  }

  public String getUpdaterName() {
    return updaterName;
  }

  public void setUpdaterName(String updaterName) {
    this.updaterName = updaterName;
  }

  public String getUpdaterDepartId() {
    return updaterDepartId;
  }

  public void setUpdaterDepartId(String updaterDepartId) {
    this.updaterDepartId = updaterDepartId;
  }

  public String getUpdaterDepartName() {
    return updaterDepartName;
  }

  public void setUpdaterDepartName(String updaterDepartName) {
    this.updaterDepartName = updaterDepartName;
  }

  public String getParentRoleId() {
    return parentRoleId;
  }

  public void setParentRoleId(String parentRoleId) {
    this.parentRoleId = parentRoleId;
  }

  @Override
  public String toString() {
    return "SysRole{"
        + "sysRoleId='"
        + sysRoleId
        + '\''
        + ", roleName='"
        + roleName
        + '\''
        + ", roleCode='"
        + roleCode
        + '\''
        + ", remark='"
        + remark
        + '\''
        + ", count="
        + count
        + ", status="
        + status
        + ", type="
        + type
        + ", category="
        + category
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
        + ", parentRoleId='"
        + parentRoleId
        + '\''
        + '}';
  }
}
