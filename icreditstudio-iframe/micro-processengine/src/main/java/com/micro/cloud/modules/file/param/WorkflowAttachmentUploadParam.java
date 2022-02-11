package com.micro.cloud.modules.file.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * 〈流程附件上传请求参数〉
 *
 * @author roy
 * @create 2021/12/8
 * @since 1.0.0
 */
public class WorkflowAttachmentUploadParam implements Serializable {

  private static final long serialVersionUID = 6465889555542995720L;

  @ApiModelProperty(
      value = "流程实例id",
      required = true,
      example = "0530996c-42ce-11ec-b5d8-5ec5f4c99a0c")
  @NotBlank(message = "流程实例id不能为空")
  private String processInstanceId;

  @ApiModelProperty(
      value = "流程当前taskId",
      required = true,
      example = "02622f7c-563f-11ec-ac65-8e8590975466")
  @NotBlank(message = "taskId不能为空")
  private String taskId;

  @ApiModelProperty(value = "流程附件描述", example = "立项招标文件xxx")
  private String attachmentDesc;

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getAttachmentDesc() {
    return attachmentDesc;
  }

  public void setAttachmentDesc(String attachmentDesc) {
    this.attachmentDesc = attachmentDesc;
  }

  @Override
  public String toString() {
    return "WorkflowAttachmentUploadParam{" +
        "processInstanceId='" + processInstanceId + '\'' +
        ", taskId='" + taskId + '\'' +
        ", attachmentDesc='" + attachmentDesc + '\'' +
        '}';
  }
}
