package com.micro.cloud.enums;

/**
 * 系统角色通用枚举
 *
 * @author roy
 */
public enum SysRoleTypeEnum {

  /** 内置角色 */
  SYSTEM(1),
  /** 自定义角色 */
  CUSTOM(2);

  SysRoleTypeEnum(Integer type) {
    this.type = type;
  }

  public Integer getType() {
    return type;
  }

  private final Integer type;
}
