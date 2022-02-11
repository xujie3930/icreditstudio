package com.micro.cloud.enums;

/**
 * 系统账号标识符
 *
 * @author roy
 */
public enum Identifier {

  /** 账号标识符: 1->username 2 ->email 3->phone 4->wechat 5->qq */
  USERNAME(1, "username"),
  EMAIl(2, "email"),
  PHONE(3, "phone"),
  WECHAT(4, "wechat"),
  QQ(5, "qq");

  /** 类型 */
  private final Integer value;
  /** 类型名 */
  private final String name;

  Identifier(Integer value, String name) {
    this.value = value;
    this.name = name;
  }

  public Integer getValue() {
    return this.value;
  }

  public String getName() {
    return this.name;
  }
}
