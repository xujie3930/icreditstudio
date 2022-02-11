package com.micro.cloud.modules.process.vo;

import com.micro.cloud.modules.file.vo.AttachmentVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.camunda.bpm.engine.task.Task;

/**
 * 〈流程表单详情视图对象〉
 *
 * @author roy
 * @create 2021/12/9
 * @since 1.0.0
 */
@ApiModel(value = "流程详情")
public class ProcessDataVO implements Serializable {

  private static final long serialVersionUID = 8309337172719453378L;

  @ApiModelProperty(value = "当前节点id", required = true, example = "")
  private String activityId;

  private String activityName;

  @ApiModelProperty(value = "当前节点所属步骤", required = true, example = "")
  private Integer step;

  @ApiModelProperty(value = "表单数据", required = true, example = "ngr:xxx...")
  @NotNull(message = "表单数据不能为空")
  private Map<String, Object> formData;

  @ApiModelProperty(value = "流程附件", required = true, example = "....")
  private List<AttachmentVO> attachments;

  @ApiModelProperty(value = "流程签字意见", required = true, example = "王武：同意 2021-11-12")
  private List<ProcessCommentVO> comments;

  @ApiModelProperty(value = "被关联流程数据", required = false)
  private List<String> associateProcesses;

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  public Integer getStep() {
    return step;
  }

  public void setStep(Integer step) {
    this.step = step;
  }

  public Map<String, Object> getFormData() {
    return formData;
  }

  public void setFormData(Map<String, Object> formData) {
    this.formData = formData;
  }

  public List<AttachmentVO> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<AttachmentVO> attachments) {
    this.attachments = attachments;
  }

  public List<ProcessCommentVO> getComments() {
    return comments;
  }

  public void setComments(List<ProcessCommentVO> comments) {
    this.comments = comments;
  }

  public List<String> getAssociateProcesses() {
    return associateProcesses;
  }

  public void setAssociateProcesses(List<String> associateProcesses) {
    this.associateProcesses = associateProcesses;
  }

  @Override
  public String toString() {
    return "ProcessDataVO{"
        + "activityId='"
        + activityId
        + '\''
        + ", activityName='"
        + activityName
        + '\''
        + ", step="
        + step
        + ", formData="
        + formData
        + ", attachments="
        + attachments
        + ", comments="
        + comments
        + ", associateProcesses="
        + associateProcesses
        + '}';
  }
}
