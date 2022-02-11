package com.micro.cloud.modules.process.listener;

import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;

/**
 * 〈流程监听器通用接口〉
 *
 * @author roy
 * @create 2021/12/18
 * @since 1.0.0
 */
public interface IProcessListener extends TaskListener, ExecutionListener {

}
