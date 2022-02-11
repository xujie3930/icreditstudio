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
@TableName("sys_role_resource_ref")
@ApiModel(value = "SysRoleResourceRef对象", description = "")
public class SysRoleResourceRef extends BaseDO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("角色权限关系id")
  @TableId("sys_role_resource_ref_id")
  private String sysRoleResourceRefId;

  @ApiModelProperty("角色id")
  @TableField("sys_role_id")
  private String sysRoleId;

  @ApiModelProperty("资源id")
  @TableField("sys_resource_id")
  private String sysResourceId;

  public String getSysRoleResourceRefId() {
    return this.sysRoleResourceRefId;
  }

  public void setSysRoleResourceRefId(String sysRoleResourceRefId) {
    this.sysRoleResourceRefId = sysRoleResourceRefId;
  }

  public String getSysRoleId() {
    return this.sysRoleId;
  }

  public void setSysRoleId(String sysRoleId) {
    this.sysRoleId = sysRoleId;
  }

  public String getSysResourceId() {
    return this.sysResourceId;
  }

  public void setSysResourceId(String sysResourceId) {
    this.sysResourceId = sysResourceId;
  }

  @Override
  public String toString() {
    return "SysRoleResourceRef{"
        + "sysRoleResourceRefId='"
        + sysRoleResourceRefId
        + '\''
        + ", sysRoleId='"
        + sysRoleId
        + '\''
        + ", sysResourceId='"
        + sysResourceId
        + '\''
        + '}';
  }
}
