package com.micro.cloud.modules.system.user.dataobject;

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
@TableName("sys_user_role_ref")
@ApiModel(value = "SysUserRoleRef对象", description = "")
public class SysUserRoleRef extends BaseDO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("用户角色id")
  @TableId("sys_user_role_ref_id")
  private String sysUserRoleRefId;

  @ApiModelProperty("用户id")
  @TableField("sys_user_id")
  private String sysUserId;

  @ApiModelProperty("角色id")
  @TableField("sys_role_id")
  private String sysRoleId;

  public String getSysUserRoleRefId() {
    return this.sysUserRoleRefId;
  }

  public void setSysUserRoleRefId(String sysUserRoleRefId) {
    this.sysUserRoleRefId = sysUserRoleRefId;
  }

  public String getSysUserId() {
    return this.sysUserId;
  }

  public void setSysUserId(String sysUserId) {
    this.sysUserId = sysUserId;
  }

  public String getSysRoleId() {
    return this.sysRoleId;
  }

  public void setSysRoleId(String sysRoleId) {
    this.sysRoleId = sysRoleId;
  }

  @Override
  public String toString() {
    return "SysUserRoleRef{"
        + "sysUserRoleRefId='"
        + sysUserRoleRefId
        + '\''
        + ", sysUserId='"
        + sysUserId
        + '\''
        + ", sysRoleId='"
        + sysRoleId
        + '\''
        + '}';
  }
}
