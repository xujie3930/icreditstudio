package com.micro.cloud.modules.process.listener;

import com.micro.cloud.modules.process.enums.ApproveStatusEnum;
import com.micro.cloud.modules.process.listener.executor.CallbackExecutor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消/拒绝至结束节点操作
 *
 * @author roy
 */
@Component
public class CancelToEndEventListener extends BaseProcessListener {
  private final Logger logger = LoggerFactory.getLogger(CancelToEndEventListener.class);

  @Autowired private CallbackExecutor callbackExecutor;

  @Override
  public void notify(DelegateExecution delegateExecution) {
    callbackExecutor.execute(delegateExecution, ApproveStatusEnum.UNAPPROVED.getValue());
  }
}
