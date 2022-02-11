package com.micro.cloud.modules.process.listener.executor;

import org.camunda.bpm.engine.delegate.DelegateExecution;

/**
 * 〈公用执行器接口〉
 *
 * @author roy
 * @create 2021/12/18
 * @since 1.0.0
 */
public interface IExecutor {

  /**
   * 执行方法
   *
   * @param delegateExecution
   */
  void execute(DelegateExecution delegateExecution, String approveStatus);
}
