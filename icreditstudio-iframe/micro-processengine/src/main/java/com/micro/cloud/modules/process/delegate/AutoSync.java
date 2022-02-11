package com.micro.cloud.modules.process.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoSync implements JavaDelegate {
  private final Logger LOGGER = LoggerFactory.getLogger(AutoSync.class);

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    LOGGER.info("本次流程实例【"+ delegateExecution.getProcessInstanceId() +"】结束-存储信息！！！" + delegateExecution.getCurrentActivityName());
  }
}
