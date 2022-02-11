package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 〈保存关联流程数据请求〉
 *
 * @author roy
 * @create 2021/12/18
 * @since 1.0.0
 */
public class SaveAssociateProcessParam implements Serializable {

  private static final long serialVersionUID = -4110779790686732161L;

  @ApiModelProperty(value = "当前流程实例id", required = true)
  @NotBlank(message = "流程实例id不能为空")
  private String processInstanceId;

  @ApiModelProperty(value = "被关联流程列表", required = true)
  @NotNull(message = "被关联流程数据不能为空")
  private List<AssociateProcessParam> associateProcessList;

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public List<AssociateProcessParam> getAssociateProcessList() {
    return associateProcessList;
  }

  public void setAssociateProcessList(List<AssociateProcessParam> associateProcessList) {
    this.associateProcessList = associateProcessList;
  }

  @Override
  public String toString() {
    return "SaveAssociateProcessParam{"
        + "processInstanceId='"
        + processInstanceId
        + '\''
        + ", associateProcessList="
        + associateProcessList
        + '}';
  }
}
