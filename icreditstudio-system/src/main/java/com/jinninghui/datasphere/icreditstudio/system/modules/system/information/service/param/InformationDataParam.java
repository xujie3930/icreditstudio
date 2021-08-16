package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.service.param;

import com.jinninghui.datasphere.icreditstudio.system.common.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.information.enums.BroadCastEnum;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.information.enums.InfoTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class InformationDataParam {

    /**
     * 消息ID
     */
    private String id;
    /**
     * 消息ID集合
     */
    private Set<String> ids;
    /**
     * 消息标题
     */
    private String infoTitle;
    /**
     * 消息类型
     */
    private InfoTypeEnum infoType;
    /**
     * 发送者ID
     */
    private String senderId;
    /**
     * 是否广播
     */
    private BroadCastEnum broadcastFlag;
    /**
     * 删除标识
     */
    private DeleteFlagEnum deleteFlag;
    /**
     * 起始发送时间
     */
    private Long startSendTime;
    /**
     * 结束发送时间
     */
    private Long endSendTime;
}
