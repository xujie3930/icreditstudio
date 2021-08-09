package com.jinninghui.datasphere.icreditstudio.modules.system.information.service.result;

import lombok.Data;

@Data
public class InfoCountResult {

    /**
     * 系统消息未读数量
     */
    private Integer sUnreadCount;
    /**
     * 通知消息未读数量
     */
    private Integer nUnreadCount;
    /**
     * 预警消息未读数量
     */
    private Integer wUnreadCount;
    /**
     * 总未读消息数量
     */
    private Integer totalUnreadCount;
}
