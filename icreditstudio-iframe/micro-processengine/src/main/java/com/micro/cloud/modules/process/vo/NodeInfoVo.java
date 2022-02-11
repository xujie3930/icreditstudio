package com.micro.cloud.modules.process.vo;

import java.io.Serializable;

public class NodeInfoVo implements Serializable {

  private static final long serialVersionUID = 5695703357428956499L;

  private boolean multiIns;

  private String paramKey;

  public NodeInfoVo() {
  }

  public boolean isMultiIns() {
    return multiIns;
  }

  public void setMultiIns(boolean multiIns) {
    this.multiIns = multiIns;
  }

  public String getParamKey() {
    return paramKey;
  }

  public void setParamKey(String paramKey) {
    this.paramKey = paramKey;
  }

  @Override
  public String toString() {
    return "NodeInfoVo{" +
        "multiIns=" + multiIns +
        ", paramKey='" + paramKey + '\'' +
        '}';
  }
}
