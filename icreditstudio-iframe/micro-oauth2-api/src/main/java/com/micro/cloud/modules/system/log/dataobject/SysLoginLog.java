package com.micro.cloud.modules.system.log.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.cloud.mybatis.core.dataobject.BaseDO;

/**
 * 登录日志表 包含登录登出
 *
 * @author roy
 */
@TableName("sys_login_log")
public class SysLoginLog extends BaseDO {

  /** 日志主键 */
  private String sysLoginLogId;
  /**
   * 日志类型
   *
   * <p>枚举 {@link com.micro.cloud.enums.SysLoginLogTypeEnum}
   */
  private Integer logType;
  /** 链路追踪编号 */
  private String traceId;
  /** 用户编号 */
  private String userId;
  /**
   * 用户类型
   *
   * <p>枚举 {@link com.micro.cloud.enums.UserTypeEnum}
   */
  private Integer userType;
  /**
   * 用户账号
   *
   * <p>冗余，因为账号可以变更
   */
  private String username;
  /**
   * 登录结果
   *
   * <p>枚举 {@link com.micro.cloud.enums.SysLoginResultEnum}
   */
  private Integer result;
  /** 用户 IP */
  private String userIp;
  /** 浏览器 UA */
  private String userAgent;

  public String getSysLoginLogId() {
    return sysLoginLogId;
  }

  public void setSysLoginLogId(String sysLoginLogId) {
    this.sysLoginLogId = sysLoginLogId;
  }

  public Integer getLogType() {
    return logType;
  }

  public void setLogType(Integer logType) {
    this.logType = logType;
  }

  public String getTraceId() {
    return traceId;
  }

  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getUserType() {
    return userType;
  }

  public void setUserType(Integer userType) {
    this.userType = userType;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getResult() {
    return result;
  }

  public void setResult(Integer result) {
    this.result = result;
  }

  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  @Override
  public String toString() {
    return "SysTenant{"
        + "sysLoginLogId='"
        + sysLoginLogId
        + '\''
        + ", logType="
        + logType
        + ", traceId='"
        + traceId
        + '\''
        + ", userId="
        + userId
        + ", userType="
        + userType
        + ", username='"
        + username
        + '\''
        + ", result="
        + result
        + ", userIp='"
        + userIp
        + '\''
        + ", userAgent='"
        + userAgent
        + '\''
        + '}';
  }
}
