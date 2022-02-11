package com.micro.cloud.enums;

/**
 * 通用状态枚举
 *
 * @author roy
 */
public enum SysCommonStatusEnum {
  DISABLE(false, "否"),
  ENABLE(true, "是");

  /** 状态值 */
  private final Boolean status;
  /** 状态名 */
  private final String name;

  SysCommonStatusEnum(Boolean status, String name) {
    this.status = status;
    this.name = name;
  }

  public Boolean getStatus() {
    return status;
  }

  public String getName() {
    return name;
  }
}
