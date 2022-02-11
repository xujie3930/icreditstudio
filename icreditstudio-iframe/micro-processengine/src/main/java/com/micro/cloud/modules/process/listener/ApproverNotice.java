package com.micro.cloud.modules.process.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApproverNotice implements TaskListener {
  private final Logger logger = LoggerFactory.getLogger(ApproverNotice.class);

  @Override
  public void notify(DelegateTask delegateTask) {
    logger.info("给审批人【 " + delegateTask.getAssignee() + " 】发送一个待办任务提醒消息！");
    // todo 消息推送
    // 获取提交节点信息,根据节点信息,根据节点信息判断是否需执行节点后操作
    String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
  }
}
