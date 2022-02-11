package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * 〈流程搜索请求参数〉
 *
 * @author roy
 * @create 2021/12/19
 * @since 1.0.0
 */
public class SearchProcessParam implements Serializable {

  private static final long serialVersionUID = -2091481311126206669L;

  @ApiModelProperty(value = "当前流程实例id", required = true)
  @NotBlank(message = "流程实例id不能为空")
  private String processInstanceId;

  @ApiModelProperty(value = "当前流程processKey", required = true)
  @NotBlank(message = "当前流程processKey不能为空")
  private String processKey;

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  @Override
  public String toString() {
    return "SearchProcessParam{"
        + "processInstanceId='"
        + processInstanceId
        + '\''
        + ", processKey='"
        + processKey
        + '\''
        + '}';
  }
}
