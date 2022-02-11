//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.micro.cloud.snowflake.sequence;

public class ClusterNode {
  private int centerId;
  private int workId;

  public ClusterNode(int centerId, int workId) {
    this.centerId = centerId;
    this.workId = workId;
  }

  public int getCenterId() {
    return this.centerId;
  }

  public void setCenterId(int centerId) {
    this.centerId = centerId;
  }

  public int getWorkId() {
    return this.workId;
  }

  public void setWorkId(int workId) {
    this.workId = workId;
  }
}
