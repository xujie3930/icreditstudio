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
@TableName("sys_user_org_ref")
@ApiModel(value = "SysUserOrgRef对象", description = "")
public class SysUserOrgRef extends BaseDO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("人员所属机构id")
  @TableId("sys_user_org_id")
  private String sysUserOrgId;

  @ApiModelProperty("人员id")
  @TableField("sys_user_id")
  private String sysUserId;

  @ApiModelProperty("机构id")
  @TableField("sys_org_id")
  private String sysOrgId;

  public String getSysUserOrgId() {
    return this.sysUserOrgId;
  }

  public void setSysUserOrgId(String sysUserOrgId) {
    this.sysUserOrgId = sysUserOrgId;
  }

  public String getSysUserId() {
    return this.sysUserId;
  }

  public void setSysUserId(String sysUserId) {
    this.sysUserId = sysUserId;
  }

  public String getSysOrgId() {
    return this.sysOrgId;
  }

  public void setSysOrgId(String sysOrgId) {
    this.sysOrgId = sysOrgId;
  }

  @Override
  public String toString() {
    return "SysUserOrgRef{" +
        "sysUserOrgId='" + sysUserOrgId + '\'' +
        ", sysUserId='" + sysUserId + '\'' +
        ", sysOrgId='" + sysOrgId + '\'' +
        '}';
  }
}
