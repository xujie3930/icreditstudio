package com.micro.cloud.modules.file.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈流程附件视图对象〉
 *
 * @author roy
 * @create 2021/12/8
 * @since 1.0.0
 */
public class AttachmentVO implements Serializable {

  @ApiModelProperty(value = "附件id", example = "", required = true, notes = "根据id进行附件下载")
  private String id;

  @ApiModelProperty(value = "附件名称", example = "立项招标文件", required = true, notes = "附件展示名称")
  private String name;

  @ApiModelProperty(
      value = "文件类型",
      example = "workflow_attachment",
      required = true,
      notes = "附件类型")
  private String type;

  @ApiModelProperty(
      value = "文件预览地址",
      example = "http://192.168.0.32:8013/demo/c60e738296404494ba8a16df0754a542.xlsx",
      required = true,
      notes = "文件在线预览地址")
  private String previewUrl;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPreviewUrl() {
    return previewUrl;
  }

  public void setPreviewUrl(String previewUrl) {
    this.previewUrl = previewUrl;
  }

  @Override
  public String toString() {
    return "AttachmentVO{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", type='"
        + type
        + '\''
        + ", previewUrl='"
        + previewUrl
        + '\''
        + '}';
  }
}
