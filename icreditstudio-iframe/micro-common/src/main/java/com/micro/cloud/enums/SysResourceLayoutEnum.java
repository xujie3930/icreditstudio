package com.micro.cloud.enums;

/**
 * 菜单类型枚举类
 *
 * @author roy
 */
public enum SysResourceLayoutEnum {
  // 顶部
  TOP(1),
  // 底部
  BOTTOM(2),
  // 左
  LEFT(3),
  // 右
  RIGHT(4);

  SysResourceLayoutEnum(Integer type) {
    this.type = type;
  }

  /** 类型 */
  private final Integer type;

  public Integer getType() {
    return type;
  }
}
