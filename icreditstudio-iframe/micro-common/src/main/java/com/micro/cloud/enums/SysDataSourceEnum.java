package com.micro.cloud.enums;

/**
 * 通用状态枚举
 *
 * @author roy
 */
public enum SysDataSourceEnum {
  /** 用户状态:开启 */
  EXTERNAL(1, "外部"),
  /** 用户状态:停用 */
  INTERNAL(2, "内部");

  /** 状态值 */
  private final Integer value;
  /** 状态名 */
  private final String name;

  SysDataSourceEnum(Integer value, String name) {
    this.value = value;
    this.name = name;
  }

  public Integer getValue() {
    return value;
  }

  public String getName() {
    return name;
  }
}
