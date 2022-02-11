package com.micro.cloud.enums;

/**
 * 全局用户类型枚举
 *
 * @author roy
 */
public enum UserTypeEnum {
  /** 用户类型: 1-> 个人用户 2 ->机构用户 3-> 系统用户 */
  EXTERNAL(1, "个人用户"),
  ORGANIZATION(2, "机构用户"),
  INTERNAL(3, "系统用户");

  /** 类型 */
  private final Integer value;
  /** 类型名 */
  private final String name;

  UserTypeEnum(Integer value, String name) {
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
