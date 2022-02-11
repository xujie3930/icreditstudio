package com.micro.cloud.modules.process.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 流程结束节点后操作
 *
 * @author roy
 */
@Component
public class EndEventNotice extends BaseProcessListener {
  private final Logger logger = LoggerFactory.getLogger(EndEventNotice.class);

  @Override
  public void notify(DelegateExecution delegateExecution) {}
}
