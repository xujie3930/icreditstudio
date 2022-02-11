package com.micro.cloud.modules.process.param;

import com.micro.cloud.api.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈流程实例分页请求参数〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
@ApiModel(value = "流程签字意见请求参数")
public class CommentParam implements Serializable {

  private static final long serialVersionUID = 8534834454860063012L;

  @ApiModelProperty(value = "流程实例id", required = true)
  private String processInstanceId;

  @ApiModelProperty(value = "任务id",required = true)
  private String taskId;

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

    @Override
    public String toString() {
        return "CommentPageParam{" +
            "processInstanceId='" + processInstanceId + '\'' +
            ", taskId='" + taskId + '\'' +
            '}';
    }
}
