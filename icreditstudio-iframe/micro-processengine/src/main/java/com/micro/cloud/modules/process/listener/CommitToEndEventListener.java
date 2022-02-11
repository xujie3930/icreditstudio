package com.micro.cloud.modules.process.listener;

import com.micro.cloud.modules.process.enums.ApproveStatusEnum;
import com.micro.cloud.modules.process.listener.executor.CallbackExecutor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 正常流转至结束节点后操作
 *
 * @author roy
 */
@Component
public class CommitToEndEventListener extends BaseProcessListener {
  private final Logger logger = LoggerFactory.getLogger(CommitToEndEventListener.class);

  @Autowired private CallbackExecutor callbackExecutor;

  @Override
  public void notify(DelegateExecution delegateExecution) {
    callbackExecutor.execute(delegateExecution, ApproveStatusEnum.APPROVED.getValue());
  }
}
