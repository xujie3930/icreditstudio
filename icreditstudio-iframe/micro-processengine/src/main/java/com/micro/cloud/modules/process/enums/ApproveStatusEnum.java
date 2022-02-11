package com.micro.cloud.modules.process.enums;

/** @author roy */
public enum ApproveStatusEnum {
  /** 审核状态；0:未发起, 1:审核中, 2:通过, 3:不通过 */
  UNCOMMITTED("0", "未发起"),
  APPROVING("1", "审核中"),
  APPROVED("2", "审核通过"),
  UNAPPROVED("3", "审核未通过");

  /** 审核状态值 */
  private final String value;
  /** 审核状态名称 */
  private final String name;

  ApproveStatusEnum(String value, String name) {
    this.value = value;
    this.name = name;
  }

  public String getValue() {
    return this.value;
  }

  public String getName() {
    return this.name;
  }
}
