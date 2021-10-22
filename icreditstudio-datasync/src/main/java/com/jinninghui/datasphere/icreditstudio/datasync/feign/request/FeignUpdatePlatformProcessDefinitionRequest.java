package com.jinninghui.datasphere.icreditstudio.datasync.feign.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author Peng
 */
@Data
@Builder
public class FeignUpdatePlatformProcessDefinitionRequest {

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
     * 通道控制
     */
    private ChannelControlParam channelControl;
    /**
     * 调度设置
     */
    private SchedulerParam schedulerParam;
}
