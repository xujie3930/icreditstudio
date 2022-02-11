package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈关联流程相关请求参数〉
 *
 * @author roy
 * @create 2021/12/17
 * @since 1.0.0
 */
public class ProcessAssociateSettingParam implements Serializable {

  private static final long serialVersionUID = -7767688223455075439L;

  @ApiModelProperty(value = "流程识别码")
  private String processKey;

  @ApiModelProperty(value = "被关联流程识别码")
  private String associatedProcessKey;

  @ApiModelProperty(value = "是否结束")
  private Boolean isFinish;

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public String getAssociatedProcessKey() {
    return associatedProcessKey;
  }

  public void setAssociatedProcessKey(String associatedProcessKey) {
    this.associatedProcessKey = associatedProcessKey;
  }

  public Boolean getFinish() {
    return isFinish;
  }

  public void setFinish(Boolean finish) {
    isFinish = finish;
  }

  @Override
  public String toString() {
    return "AssociateProcessConditionParam{" +
        "processKey='" + processKey + '\'' +
        ", associatedProcessKey='" + associatedProcessKey + '\'' +
        ", isFinish=" + isFinish +
        '}';
  }
}
