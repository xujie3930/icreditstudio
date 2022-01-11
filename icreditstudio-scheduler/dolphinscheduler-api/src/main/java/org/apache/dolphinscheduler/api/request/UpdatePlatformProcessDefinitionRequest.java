package org.apache.dolphinscheduler.api.request;

import lombok.Data;
import org.apache.dolphinscheduler.api.param.ChannelControlParam;
import org.apache.dolphinscheduler.api.param.PlatformPartitionParam;
import org.apache.dolphinscheduler.api.param.PlatformTaskOrdinaryParam;
import org.apache.dolphinscheduler.api.param.SchedulerParam;
import org.apache.dolphinscheduler.dao.entity.User;

/**
 * @author Peng
 */
@Data
public class UpdatePlatformProcessDefinitionRequest {

    /**
     * 工作流定义ID
     */
    private String processDefinitionId;
    /**
     * 访问用户
     */
    private User accessUser;
    /**
     * 普通参数
     */
    private PlatformTaskOrdinaryParam ordinaryParam;

    /**
     * 分区参数
     */
    private PlatformPartitionParam partitionParam;
    /**
     * 通道控制
     */
    private ChannelControlParam channelControl;
    /**
     * 调度设置
     */
    private SchedulerParam schedulerParam;
}
