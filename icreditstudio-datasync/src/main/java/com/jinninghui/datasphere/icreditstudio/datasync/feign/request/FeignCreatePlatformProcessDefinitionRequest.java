package com.jinninghui.datasphere.icreditstudio.datasync.feign.request;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.SyncCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Peng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeignCreatePlatformProcessDefinitionRequest {
    /**
     * 访问用户
     */
    private User accessUser;
    /**
     * 普通参数
     */
    private PlatformTaskOrdinaryParam ordinaryParam;
    /**
     * 同步参数
     */
    private SyncCondition partitionParam;
    /**
     * 通道控制
     */
    private ChannelControlParam channelControl;
    /**
     * 调度设置
     */
    private SchedulerParam schedulerParam;
}
