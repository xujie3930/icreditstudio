package com.micro.cloud.modules.system.user.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author EDZ
 * @since 2021-11-05
 */
@TableName("sys_user_account")
@ApiModel(value = "SysUserAccount对象", description = "")
public class SysUserAccount extends BaseDO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("个人用户账号id")
  @TableId("sys_user_account_id")
  private String sysUserAccountId;

  @ApiModelProperty("账户是否过期: 0->否 1->是")
  @TableField("account_expired")
  private Boolean accountExpired;

  @ApiModelProperty("账户是否锁定:0 ->否 1 ->是")
  @TableField("account_locked")
  private Boolean accountLocked;

  @ApiModelProperty("账户凭据")
  @TableField("credential")
  private String credential;

  @ApiModelProperty("账户凭据是否过期: 0 ->否 1->是")
  @TableField("credential_expired")
  private Boolean credentialExpired;

  @ApiModelProperty("账户标识符:	1:username,2:email,3:phone,4:wechat,5:qq")
  @TableField("identifier")
  private Integer identifier;

  @ApiModelProperty("登录模式")
  @TableField("login_mode")
  private Integer loginMode;

  @ApiModelProperty("账户状态: 0->启用 1-> 停用 2->待激活")
  @TableField("status")
  private Boolean status;

  @ApiModelProperty("个人用户id")
  @TableField("sys_user_id")
  private String sysUserId;

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

  @ApiModelProperty("上次登录IP")
  @TableField("last_login_ip")
  private String lastLoginIp;

  @ApiModelProperty("上次登录时间")
  @TableField("last_login_time")
  private LocalDateTime lastLoginTime;

  @ApiModelProperty("上次更改密码时间")
  @TableField("last_modify_pwd_time")
  private LocalDateTime lastModifyPwdTime;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getSysUserAccountId() {
    return sysUserAccountId;
  }

  public void setSysUserAccountId(String sysUserAccountId) {
    this.sysUserAccountId = sysUserAccountId;
  }

  public Boolean getAccountExpired() {
    return accountExpired;
  }

  public void setAccountExpired(Boolean accountExpired) {
    this.accountExpired = accountExpired;
  }

  public Boolean getAccountLocked() {
    return accountLocked;
  }

  public void setAccountLocked(Boolean accountLocked) {
    this.accountLocked = accountLocked;
  }

  public String getCredential() {
    return credential;
  }

  public void setCredential(String credential) {
    this.credential = credential;
  }

  public Boolean getCredentialExpired() {
    return credentialExpired;
  }

  public void setCredentialExpired(Boolean credentialExpired) {
    this.credentialExpired = credentialExpired;
  }

  public Integer getIdentifier() {
    return identifier;
  }

  public void setIdentifier(Integer identifier) {
    this.identifier = identifier;
  }

  public Integer getLoginMode() {
    return loginMode;
  }

  public void setLoginMode(Integer loginMode) {
    this.loginMode = loginMode;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public String getSysUserId() {
    return sysUserId;
  }

  public void setSysUserId(String sysUserId) {
    this.sysUserId = sysUserId;
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

  public String getLastLoginIp() {
    return lastLoginIp;
  }

  public void setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  public LocalDateTime getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(LocalDateTime lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public LocalDateTime getLastModifyPwdTime() {
    return lastModifyPwdTime;
  }

  public void setLastModifyPwdTime(LocalDateTime lastModifyPwdTime) {
    this.lastModifyPwdTime = lastModifyPwdTime;
  }

  @Override
  public String toString() {
    return "SysUserAccount{"
        + "sysUserAccountId='"
        + sysUserAccountId
        + '\''
        + ", accountExpired="
        + accountExpired
        + ", accountLocked="
        + accountLocked
        + ", credential='"
        + credential
        + '\''
        + ", credentialExpired="
        + credentialExpired
        + ", identifier='"
        + identifier
        + '\''
        + ", loginMode="
        + loginMode
        + ", status="
        + status
        + ", sysUserId='"
        + sysUserId
        + '\''
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
        + ", lastLoginIp='"
        + lastLoginIp
        + '\''
        + ", lastLoginTime="
        + lastLoginTime
        + ", lastModifyPwdTime="
        + lastModifyPwdTime
        + '}';
  }
}
