package com.micro.cloud.enums;

/**
 * 登录结果的枚举类
 *
 * @author roy
 */
public enum SysLoginResultEnum {
  /** 登录失败 */
  FAILURE(0),
  /** 登录成功 */
  SUCCESS(1),
  /** 账号或密码不正确 */
  BAD_CREDENTIALS(10),
  /** 用户被禁用 */
  USER_DISABLED(20),
  /** 图片验证码不存在 */
  CAPTCHA_NOT_FOUND(30),
  /** 图片验证码不正确 */
  CAPTCHA_CODE_ERROR(31),
  /** 未知异常 */
  UNKNOWN_ERROR(100);

  SysLoginResultEnum(Integer result) {
    this.result = result;
  }

  /** 结果 */
  private final Integer result;

  public Integer getResult() {
    return result;
  }
}
