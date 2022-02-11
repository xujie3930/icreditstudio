package com.micro.cloud.modules.process.vo;

import java.io.Serializable;
import java.util.Set;

public class ActivityHighLineVO implements Serializable {

  private static final long serialVersionUID = 2984953595151501085L;

  private Set<String> highPoint;

  private Set<String> highLine;

  private Set<String> waitingToDo;

  private Set<String> iDo;

  private String bpmn;

  public ActivityHighLineVO() {
  }

  public String getBpmn() {
    return bpmn;
  }

  public void setBpmn(String bpmn) {
    this.bpmn = bpmn;
  }

  public Set<String> getHighLine() {
    return highLine;
  }

  public void setHighLine(Set<String> highLine) {
    this.highLine = highLine;
  }

  public Set<String> getHighPoint() {
    return highPoint;
  }

  public void setHighPoint(Set<String> highPoint) {
    this.highPoint = highPoint;
  }

  public Set<String> getWaitingToDo() {
    return waitingToDo;
  }

  public void setWaitingToDo(Set<String> waitingToDo) {
    this.waitingToDo = waitingToDo;
  }

  public Set<String> getiDo() {
    return iDo;
  }

  public void setiDo(Set<String> iDo) {
    this.iDo = iDo;
  }
}
