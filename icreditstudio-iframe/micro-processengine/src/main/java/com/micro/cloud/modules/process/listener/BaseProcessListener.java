package com.micro.cloud.modules.process.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;

/**
 * 〈流程监听器通用实现类〉
 *
 * @author roy
 * @create 2021/12/18
 * @since 1.0.0
 */
public abstract class BaseProcessListener implements IProcessListener{

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}