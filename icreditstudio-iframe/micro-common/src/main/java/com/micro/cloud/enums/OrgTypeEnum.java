package com.micro.cloud.enums;

/**
 * @author roy
 */

public enum OrgTypeEnum {
  /** 机构类型: 1-> 法人机构 2 ->非法人机构 3-> 系统组织机构 4->供应商 */
  EXTERNAL(1, "法人机构"),
  ORGANIZATION(2, "非法人机构"),
  INTERNAL(3, "系统组织机构"),
  SUPPLIER(4,"供应商");

  /** 类型 */
  private final Integer value;
  /** 类型名 */
  private final String name;

  OrgTypeEnum(Integer value, String name) {
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
