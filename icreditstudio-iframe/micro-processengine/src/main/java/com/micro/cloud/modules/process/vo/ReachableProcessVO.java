package com.micro.cloud.modules.process.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 〈可发起流程数据展示〉
 *
 * @author roy
 * @create 2021/12/8
 * @since 1.0.0
 */
public class ReachableProcessVO implements Serializable {

  private static final long serialVersionUID = 3458550939115821957L;

  @ApiModelProperty(value = "流程识别码", required = true, example = "Process_0exdzpp")
  private String processKey;

  @ApiModelProperty(value = "流程名称", required = true, example = "请假申请")
  private String processName;

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Override
    public String toString() {
        return "ReachableProcessVO{" +
            "processKey='" + processKey + '\'' +
            ", processName='" + processName + '\'' +
            '}';
    }
}
