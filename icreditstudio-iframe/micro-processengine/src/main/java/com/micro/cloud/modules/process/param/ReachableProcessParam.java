package com.micro.cloud.modules.process.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈可获取流程列表请求参数〉
 *
 * @author roy
 * @create 2021/12/8
 * @since 1.0.0
 */
@ApiModel(value = "可获取流程查询参数")
public class ReachableProcessParam implements Serializable {

  private static final long serialVersionUID = 3155900907508760893L;

  @ApiModelProperty(value = "流程名称", required = false, example = "项目立项", notes = "模糊查询时使用")
  private String processName;

  public String getProcessName() {
    return processName;
  }

  public void setProcessName(String processName) {
    this.processName = processName;
  }

  @Override
  public String toString() {
    return "ReachableProcessParam{" + "processName='" + processName + '\'' + '}';
  }
}
