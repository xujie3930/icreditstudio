package com.micro.cloud.modules.process.result;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈附件回调返回结果〉
 *
 * @author roy
 * @create 2021/12/20
 * @since 1.0.0
 */
public class CallbackAttachmentResult implements Serializable {

  private static final long serialVersionUID = 5312467432046010394L;

  @ApiModelProperty(value = "")
  private String bizId;

  @ApiModelProperty(value = "文件大小")
  private String fileSize;

  @ApiModelProperty(value = "文件名称")
  private String fileName;

  @ApiModelProperty(value = "文件预览地址")
  private String filePreviewUrl;

  @ApiModelProperty(value = "文件地址")
  private String fileUrl;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePreviewUrl() {
        return filePreviewUrl;
    }

    public void setFilePreviewUrl(String filePreviewUrl) {
        this.filePreviewUrl = filePreviewUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "CallbackAttachmentResult{" +
            "bizId='" + bizId + '\'' +
            ", fileSize='" + fileSize + '\'' +
            ", fileName='" + fileName + '\'' +
            ", filePreviewUrl='" + filePreviewUrl + '\'' +
            ", fileUrl='" + fileUrl + '\'' +
            '}';
    }
}
