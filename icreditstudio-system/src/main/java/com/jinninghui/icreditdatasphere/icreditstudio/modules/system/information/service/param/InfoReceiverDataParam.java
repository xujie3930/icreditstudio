package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.service.param;

import com.jinninghui.icreditdatasphere.icreditstudio.common.enums.DeleteFlagEnum;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.enums.ReadStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Created by PPai on 2021/6/17 11:19
 */
@Data
@Builder
public class InfoReceiverDataParam {
    /**
     * 消息ID
     */
    private String infoId;
    /**
     * 消息ID集合
     */
    private Set<String> infoIds;
    /**
     * 接收人ID
     */
    private String receiverId;
    /**
     * 接收人ID集合
     */
    private Set<String> receiverIds;
    /**
     * 阅读状态
     */
    private ReadStatusEnum readStatus;

    private DeleteFlagEnum deleteFlag;
}
