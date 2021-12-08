package org.apache.dolphinscheduler.service.handler;

import org.apache.dolphinscheduler.service.DialectKeyRegister;
import org.apache.dolphinscheduler.service.quartz.PlatformPartitionParam;

/**
 * @author Peng
 */
public interface ProcessDefinitionJsonHandler  extends DialectKeyRegister {

    /**
     * 处理processDefinitionJson字符串数据
     *
     * @param partitionParam        处理条件
     * @param processDefinitionJson 流程定义json字符串
     * @return
     */
    String handler(PlatformPartitionParam partitionParam, String processDefinitionJson);
}
