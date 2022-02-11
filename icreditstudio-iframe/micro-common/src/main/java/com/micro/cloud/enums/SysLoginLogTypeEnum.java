package com.micro.cloud.enums;

/**
 * 登录日志的类型枚举
 *
 * @author roy
 */
public enum SysLoginLogTypeEnum {
  /** 使用账号登录 */
  LOGIN_USERNAME(100),
  /** 使用社交登录 */
  LOGIN_SOCIAL(101),
  /** 使用手机登陆 */
  LOGIN_MOBILE(102),
  /** 主动登出 */
  LOGOUT_SELF(200),
  /** 超时登出 */
  LOGOUT_TIMEOUT(201),
  /** 被踢下线 */
  LOGOUT_DELETE(202);

  SysLoginLogTypeEnum(Integer type) {
    this.type = type;
  }

  /** 日志类型 */
  private final Integer type;

  public Integer getType() {
    return type;
  }
}
