package com.micro.cloud.modules.process.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmpNotice implements TaskListener {
  private final Logger LOGGER = LoggerFactory.getLogger(EmpNotice.class);

  @Override
  public void notify(DelegateTask delegateTask) {
    LOGGER.info("给申请人【 " + delegateTask.getAssignee() + " 】发送一个待办任务提醒消息！");
  }


}
