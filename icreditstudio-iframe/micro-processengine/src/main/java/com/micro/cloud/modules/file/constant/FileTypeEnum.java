package com.micro.cloud.modules.file.constant;

/**
 * 文件类型枚举累
 *
 * @author roy
 */
public enum FileTypeEnum {
  /** 文件类型 */
  WORKFLOW_ATTACHMENT("workflow_attachment", "流程附件");

  private final String fileType;

  private final String desc;

  FileTypeEnum(String fileType, String desc) {
    this.fileType = fileType;
    this.desc = desc;
  }

  public String getFileType() {
    return fileType;
  }

  public String getDesc() {
    return desc;
  }
}
